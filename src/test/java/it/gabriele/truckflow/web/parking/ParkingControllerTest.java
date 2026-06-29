package it.gabriele.truckflow.web.parking;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
class ParkingControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void shouldListSeededParkingSpots() throws Exception {
    mockMvc
        .perform(get("/api/parking/spots"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("DEPOT-MIL-01")))
        .andExpect(content().string(containsString("A12")));
  }

  @Test
  void shouldAssignVanToParkingSpot() throws Exception {
    String json =
        """
        {
          "assignmentCode": "PARK-API-001",
          "parkingSpotId": "DEPOT-MIL-01:100",
          "resourceType": "VAN",
          "resourceId": "VAN-001",
          "displayName": "Furgone Iveco Daily VAN-001",
          "totalLengthMeters": 5.4,
          "startedAt": "2026-06-29T16:30:00",
          "notes": "Creato dal test REST"
        }
        """;

    mockMvc
        .perform(
            post("/api/parking/assignments").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.assignmentCode").value("PARK-API-001"))
        .andExpect(jsonPath("$.facilityCode").value("DEPOT-MIL-01"))
        .andExpect(jsonPath("$.spotNumber").value("100"))
        .andExpect(jsonPath("$.resourceId").value("VAN-001"))
        .andExpect(jsonPath("$.active").value(true));
  }

  @Test
  void shouldReturnBadRequestForUnsupportedParkingResource() throws Exception {
    String json =
        """
        {
          "assignmentCode": "PARK-API-ERR",
          "parkingSpotId": "DEPOT-MIL-01:100",
          "resourceType": "OTHER",
          "resourceId": "OTHER-001",
          "displayName": "Risorsa generica",
          "totalLengthMeters": 5.4,
          "startedAt": "2026-06-29T16:30:00"
        }
        """;

    mockMvc
        .perform(
            post("/api/parking/assignments").contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.message")
                .value(
                    "Il tipo risorsa OTHER non è ancora supportato dalla prima API parcheggio."));
  }
}
