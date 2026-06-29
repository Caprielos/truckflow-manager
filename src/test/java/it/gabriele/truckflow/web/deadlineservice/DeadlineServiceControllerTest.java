package it.gabriele.truckflow.web.deadlineservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DeadlineServiceControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void shouldExposeManagedElementCatalog() throws Exception {
    mockMvc
        .perform(get("/api/deadline-service/managed-elements"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[?(@.code == 'VEHICLE_ENGINE_OIL')]").exists())
        .andExpect(jsonPath("$[?(@.code == 'TRAILER_REFRIGERATION_UNIT')]").exists())
        .andExpect(jsonPath("$[?(@.code == 'TELEMATICS_DTC_ENGINE_ERROR')]").exists());
  }

  @Test
  void shouldExposeActiveRulePackSummary() throws Exception {
    mockMvc
        .perform(get("/api/deadline-service/rule-pack"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("truckflow-default-deadline-rule-pack"))
        .andExpect(jsonPath("$.version").value("2026.1"))
        .andExpect(jsonPath("$.status").value("DRAFT"))
        .andExpect(jsonPath("$.emptySlotsCount").isNumber());
  }

  @Test
  void shouldEvaluateSingleGenericSubject() throws Exception {
    String json =
        """
        {
          "evaluationDate": "2026-06-30",
          "subject": {
            "objectRef": {
              "tenantId": "DEFAULT",
              "objectType": "VEHICLE",
              "objectId": "VEH-001",
              "naturalKey": "AB123CD"
            },
            "configuredCountry": "IT",
            "manufacturer": "IVECO",
            "model": "S-WAY",
            "elements": ["VEHICLE_ENGINE_OIL"],
            "facts": {
              "currentKm": "180000"
            }
          }
        }
        """;

    mockMvc
        .perform(
            post("/api/deadline-service/evaluations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.tenantId").value("DEFAULT"))
        .andExpect(jsonPath("$.objectType").value("VEHICLE"))
        .andExpect(jsonPath("$.objectId").value("VEH-001"))
        .andExpect(jsonPath("$.overallStatus").value("CONFIGURATION_MISSING"))
        .andExpect(jsonPath("$.canOperate").value(true))
        .andExpect(jsonPath("$.evaluations[0].elementCode").value("VEHICLE_ENGINE_OIL"))
        .andExpect(jsonPath("$.evaluations[0].sourceRuleId").value("SLOT_VEHICLE_ENGINE_OIL"));
  }

  @Test
  void shouldEvaluateBatchGenericSubjects() throws Exception {
    String json =
        """
        {
          "evaluationDate": "2026-06-30",
          "subjects": [
            {
              "objectRef": {
                "tenantId": "DEFAULT",
                "objectType": "VEHICLE",
                "objectId": "VEH-001",
                "naturalKey": "AB123CD"
              },
              "configuredCountry": "IT",
              "manufacturer": "IVECO",
              "model": "S-WAY",
              "elements": ["VEHICLE_ENGINE_OIL"],
              "facts": {}
            },
            {
              "objectRef": {
                "tenantId": "DEFAULT",
                "objectType": "TRAILER",
                "objectId": "TRL-001",
                "naturalKey": "TRAILER-001"
              },
              "configuredCountry": "IT",
              "manufacturer": "SCHMITZ",
              "model": "CARGOBULL",
              "elements": ["TRAILER_REFRIGERATION_UNIT"],
              "facts": {}
            }
          ]
        }
        """;

    mockMvc
        .perform(
            post("/api/deadline-service/evaluations/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].objectId").value("VEH-001"))
        .andExpect(jsonPath("$[1].objectId").value("TRL-001"))
        .andExpect(jsonPath("$[0].overallStatus").value("CONFIGURATION_MISSING"))
        .andExpect(jsonPath("$[1].overallStatus").value("CONFIGURATION_MISSING"));
  }

  @Test
  void shouldRejectInvalidEvaluationRequest() throws Exception {
    String json =
        """
        {
          "evaluationDate": "2026-06-30",
          "subject": {
            "objectRef": {
              "tenantId": "",
              "objectType": "VEHICLE",
              "objectId": "VEH-001"
            },
            "elements": []
          }
        }
        """;

    mockMvc
        .perform(
            post("/api/deadline-service/evaluations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Richiesta non valida."));
  }
}
