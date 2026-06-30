package it.gabriele.truckflow.domain.qualifications;

public enum Qualification {
  DRIVING_LICENSE_C(
      "DRIVING_LICENSE_C",
      "Driving License C",
      QualificationCategory.DRIVING_LICENSES,
      "License for heavy trucks over 3.5 tonnes"),

  DRIVING_LICENSE_C1(
      "DRIVING_LICENSE_C1",
      "Driving License C1",
      QualificationCategory.DRIVING_LICENSES,
      "License for trucks between 3.5 and 7.5 tonnes"),

  DRIVING_LICENSE_CE(
      "DRIVING_LICENSE_CE",
      "Driving License CE",
      QualificationCategory.DRIVING_LICENSES,
      "License for articulated vehicles and road trains"),

  DRIVING_LICENSE_C1E(
      "DRIVING_LICENSE_C1E",
      "Driving License C1E",
      QualificationCategory.DRIVING_LICENSES,
      "License for C1 vehicles with a heavy trailer"),

  DRIVING_LICENSE_BE(
      "DRIVING_LICENSE_BE",
      "Driving License BE",
      QualificationCategory.DRIVING_LICENSES,
      "License for category B vehicles with a trailer"),

  DRIVING_LICENSE_D(
      "DRIVING_LICENSE_D",
      "Driving License D",
      QualificationCategory.DRIVING_LICENSES,
      "License for buses"),

  DRIVING_LICENSE_D1(
      "DRIVING_LICENSE_D1",
      "Driving License D1",
      QualificationCategory.DRIVING_LICENSES,
      "License for minibuses"),

  DRIVING_LICENSE_DE(
      "DRIVING_LICENSE_DE",
      "Driving License DE",
      QualificationCategory.DRIVING_LICENSES,
      "License for buses with a trailer"),

  DRIVING_LICENSE_D1E(
      "DRIVING_LICENSE_D1E",
      "Driving License D1E",
      QualificationCategory.DRIVING_LICENSES,
      "License for minibuses with a trailer"),

  CQC_GOODS(
      "CQC_GOODS",
      "CQC Goods",
      QualificationCategory.CQC,
      "Professional qualification for road goods transport"),

  CQC_PASSENGERS(
      "CQC_PASSENGERS",
      "CQC Passengers",
      QualificationCategory.CQC,
      "Professional qualification for passenger transport"),

  ADR_BASIC(
      "ADR_BASIC",
      "ADR Basic",
      QualificationCategory.ADR,
      "Basic qualification for dangerous goods transport"),

  ADR_TANK(
      "ADR_TANK",
      "ADR Tank",
      QualificationCategory.ADR,
      "Qualification for dangerous goods transport in tanks"),

  ADR_CLASS_1(
      "ADR_CLASS_1",
      "ADR Class 1",
      QualificationCategory.ADR,
      "Qualification for explosive substances and articles"),

  ADR_CLASS_2("ADR_CLASS_2", "ADR Class 2", QualificationCategory.ADR, "Qualification for gases"),

  ADR_CLASS_3(
      "ADR_CLASS_3",
      "ADR Class 3",
      QualificationCategory.ADR,
      "Qualification for flammable liquids"),

  ADR_CLASS_4(
      "ADR_CLASS_4",
      "ADR Class 4",
      QualificationCategory.ADR,
      "Qualification for flammable solids"),

  ADR_CLASS_5(
      "ADR_CLASS_5",
      "ADR Class 5",
      QualificationCategory.ADR,
      "Qualification for oxidizing substances and organic peroxides"),

  ADR_CLASS_6(
      "ADR_CLASS_6",
      "ADR Class 6",
      QualificationCategory.ADR,
      "Qualification for toxic and infectious substances"),

  ADR_CLASS_7(
      "ADR_CLASS_7",
      "ADR Class 7",
      QualificationCategory.ADR,
      "Qualification for radioactive material"),

  ADR_CLASS_8(
      "ADR_CLASS_8",
      "ADR Class 8",
      QualificationCategory.ADR,
      "Qualification for corrosive substances"),

  ADR_CLASS_9(
      "ADR_CLASS_9",
      "ADR Class 9",
      QualificationCategory.ADR,
      "Qualification for miscellaneous dangerous substances and articles"),

  ATP(
      "ATP",
      "ATP",
      QualificationCategory.FOOD_PHARMACEUTICALS,
      "Certification for refrigerated transport vehicles"),

  HACCP(
      "HACCP",
      "HACCP",
      QualificationCategory.FOOD_PHARMACEUTICALS,
      "Training for food handling and food safety procedures"),

  PHARMACEUTICAL_TRANSPORT(
      "PHARMACEUTICAL_TRANSPORT",
      "Pharmaceutical Transport",
      QualificationCategory.FOOD_PHARMACEUTICALS,
      "Qualification for pharmaceutical and healthcare product transport"),

  LIVE_ANIMALS(
      "LIVE_ANIMALS",
      "Live Animals",
      QualificationCategory.ANIMALS,
      "Qualification for live animal transport"),

  ANIMAL_WELFARE(
      "ANIMAL_WELFARE",
      "Animal Welfare",
      QualificationCategory.ANIMALS,
      "Training for animal welfare requirements during transport"),

  SLAUGHTER_ANIMALS(
      "SLAUGHTER_ANIMALS",
      "Slaughter Animals",
      QualificationCategory.ANIMALS,
      "Qualification for transport of animals intended for slaughter"),

  PET_ANIMALS(
      "PET_ANIMALS",
      "Pet Animals",
      QualificationCategory.ANIMALS,
      "Qualification for transport of companion animals"),

  WASTE_CATEGORY_1(
      "WASTE_CATEGORY_1",
      "Waste Category 1",
      QualificationCategory.WASTE,
      "Authorization for waste transport category 1"),

  WASTE_CATEGORY_2(
      "WASTE_CATEGORY_2",
      "Waste Category 2",
      QualificationCategory.WASTE,
      "Authorization for waste transport category 2"),

  WASTE_CATEGORY_3(
      "WASTE_CATEGORY_3",
      "Waste Category 3",
      QualificationCategory.WASTE,
      "Authorization for waste transport category 3"),

  WASTE_CATEGORY_4(
      "WASTE_CATEGORY_4",
      "Waste Category 4",
      QualificationCategory.WASTE,
      "Authorization for waste transport category 4"),

  WASTE_CATEGORY_5(
      "WASTE_CATEGORY_5",
      "Waste Category 5",
      QualificationCategory.WASTE,
      "Authorization for waste transport category 5"),

  WASTE_CATEGORY_6(
      "WASTE_CATEGORY_6",
      "Waste Category 6",
      QualificationCategory.WASTE,
      "Authorization for waste transport category 6"),

  WASTE_CATEGORY_8(
      "WASTE_CATEGORY_8",
      "Waste Category 8",
      QualificationCategory.WASTE,
      "Authorization for waste transport category 8"),

  WASTE_CATEGORY_9(
      "WASTE_CATEGORY_9",
      "Waste Category 9",
      QualificationCategory.WASTE,
      "Authorization for waste transport category 9"),

  WASTE_CATEGORY_10(
      "WASTE_CATEGORY_10",
      "Waste Category 10",
      QualificationCategory.WASTE,
      "Authorization for waste transport category 10"),

  FORKLIFT(
      "FORKLIFT",
      "Forklift",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for forklift operation"),

  MEWP(
      "MEWP",
      "MEWP",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for mobile elevating work platform operation"),

  TRUCK_MOUNTED_CRANE(
      "TRUCK_MOUNTED_CRANE",
      "Truck Mounted Crane",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for truck mounted crane operation"),

  TOWER_CRANE(
      "TOWER_CRANE",
      "Tower Crane",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for tower crane operation"),

  EXCAVATOR(
      "EXCAVATOR",
      "Excavator",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for excavator operation"),

  WHEEL_LOADER(
      "WHEEL_LOADER",
      "Wheel Loader",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for wheel loader operation"),

  BACKHOE_LOADER(
      "BACKHOE_LOADER",
      "Backhoe Loader",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for backhoe loader operation"),

  TELEHANDLER(
      "TELEHANDLER",
      "Telehandler",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for telehandler operation"),

  CONSTRUCTION_SITE_SAFETY(
      "CONSTRUCTION_SITE_SAFETY",
      "Construction Site Safety",
      QualificationCategory.SAFETY,
      "Safety training for construction site operations"),

  PPE_CATEGORY_III(
      "PPE_CATEGORY_III",
      "PPE Category III",
      QualificationCategory.SAFETY,
      "Training for use of category III personal protective equipment"),

  ROAD_SIGNAGE(
      "ROAD_SIGNAGE",
      "Road Signage",
      QualificationCategory.SAFETY,
      "Training for road signage and roadside work activities"),

  LOAD_HANDLING(
      "LOAD_HANDLING",
      "Load Handling",
      QualificationCategory.SAFETY,
      "Training for safe handling and movement of loads"),

  FIRE_SAFETY(
      "FIRE_SAFETY",
      "Fire Safety",
      QualificationCategory.SAFETY,
      "Training for fire prevention and emergency response"),

  FIRST_AID(
      "FIRST_AID",
      "First Aid",
      QualificationCategory.SAFETY,
      "Training for workplace first aid duties"),

  CONTAINER_TRANSPORT(
      "CONTAINER_TRANSPORT",
      "Container Transport",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for container transport in port and terminal areas"),

  IMO(
      "IMO",
      "IMO",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for maritime dangerous goods handling"),

  PORT_AREA_ACCESS(
      "PORT_AREA_ACCESS",
      "Port Area Access",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Permit or qualification for port area access"),

  PORT_ADR(
      "PORT_ADR",
      "Port ADR",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for ADR operations in port environments"),

  AIRPORT_CARGO(
      "AIRPORT_CARGO",
      "Airport Cargo",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for cargo operations in airport environments"),

  AIRPORT_SECURITY(
      "AIRPORT_SECURITY",
      "Airport Security",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Training for airport security requirements"),

  LOGISTICS(
      "LOGISTICS",
      "Logistics",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for company logistics operations"),

  WAREHOUSE_MANAGEMENT(
      "WAREHOUSE_MANAGEMENT",
      "Warehouse Management",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for warehouse operations management"),

  LOAD_SLINGING(
      "LOAD_SLINGING",
      "Load Slinging",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for load slinging and securing activities"),

  RAMPS_AND_DOCKS(
      "RAMPS_AND_DOCKS",
      "Ramps and Docks",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for loading ramps, docks and loading areas"),

  SENSITIVE_GOODS_TRANSPORT(
      "SENSITIVE_GOODS_TRANSPORT",
      "Sensitive Goods Transport",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for handling and transport of sensitive goods"),

  MEDICAL_EQUIPMENT_TRANSPORT(
      "MEDICAL_EQUIPMENT_TRANSPORT",
      "Medical Equipment Transport",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for medical equipment transport");

  private final String code;
  private final String name;
  private final QualificationCategory category;
  private final String shortDescription;

  Qualification(String code, String name, QualificationCategory category, String shortDescription) {
    this.code = code;
    this.name = name;
    this.category = category;
    this.shortDescription = shortDescription;
  }

  public String code() {
    return code;
  }

  public String nameValue() {
    return name;
  }

  public QualificationCategory category() {
    return category;
  }

  public String shortDescription() {
    return shortDescription;
  }
}
