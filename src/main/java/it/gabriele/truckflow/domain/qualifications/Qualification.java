package it.gabriele.truckflow.domain.qualifications;

public enum Qualification {
  DRIVING_LICENSE_C(
      "DRIVING_LICENSE_C",
      "Driving License C",
      QualificationCategory.DRIVING_LICENSES,
      "License for heavy trucks over 3.5 tonnes.",
      "Allows the holder to drive heavy goods vehicles with a maximum authorized mass above 3.5"
          + " tonnes, according to the applicable driving license rules."),

  DRIVING_LICENSE_C1(
      "DRIVING_LICENSE_C1",
      "Driving License C1",
      QualificationCategory.DRIVING_LICENSES,
      "License for trucks between 3.5 and 7.5 tonnes.",
      "Allows the holder to drive medium goods vehicles with a maximum authorized mass above 3.5"
          + " tonnes and up to 7.5 tonnes."),

  DRIVING_LICENSE_CE(
      "DRIVING_LICENSE_CE",
      "Driving License CE",
      QualificationCategory.DRIVING_LICENSES,
      "License for articulated vehicles and road trains.",
      "Allows the holder to drive category C vehicles combined with heavy trailers or"
          + " semi-trailers, such as articulated vehicles and road trains."),

  DRIVING_LICENSE_C1E(
      "DRIVING_LICENSE_C1E",
      "Driving License C1E",
      QualificationCategory.DRIVING_LICENSES,
      "License for C1 vehicles with a heavy trailer.",
      "Allows the holder to drive category C1 vehicles combined with trailers exceeding the"
          + " ordinary trailer limits."),

  DRIVING_LICENSE_BE(
      "DRIVING_LICENSE_BE",
      "Driving License BE",
      QualificationCategory.DRIVING_LICENSES,
      "License for category B vehicles with a trailer.",
      "Allows the holder to drive category B vehicles combined with trailers exceeding the standard"
          + " limits allowed by a basic B license."),

  DRIVING_LICENSE_D(
      "DRIVING_LICENSE_D",
      "Driving License D",
      QualificationCategory.DRIVING_LICENSES,
      "License for buses.",
      "Allows the holder to drive buses and vehicles designed for the transport of passengers."),

  DRIVING_LICENSE_D1(
      "DRIVING_LICENSE_D1",
      "Driving License D1",
      QualificationCategory.DRIVING_LICENSES,
      "License for minibuses.",
      "Allows the holder to drive minibuses and passenger vehicles with limited passenger"
          + " capacity."),

  DRIVING_LICENSE_DE(
      "DRIVING_LICENSE_DE",
      "Driving License DE",
      QualificationCategory.DRIVING_LICENSES,
      "License for buses with a trailer.",
      "Allows the holder to drive category D passenger vehicles combined with trailers."),

  DRIVING_LICENSE_D1E(
      "DRIVING_LICENSE_D1E",
      "Driving License D1E",
      QualificationCategory.DRIVING_LICENSES,
      "License for minibuses with a trailer.",
      "Allows the holder to drive category D1 passenger vehicles combined with trailers."),

  CQC_GOODS(
      "CQC_GOODS",
      "CQC Goods",
      QualificationCategory.CQC,
      "Professional qualification for road goods transport.",
      "Professional driver qualification required for drivers performing goods transport activities"
          + " in regulated professional contexts."),

  CQC_PASSENGERS(
      "CQC_PASSENGERS",
      "CQC Passengers",
      QualificationCategory.CQC,
      "Professional qualification for passenger transport.",
      "Professional driver qualification required for drivers performing passenger transport"
          + " activities in regulated professional contexts."),

  ADR_BASIC(
      "ADR_BASIC",
      "ADR Basic",
      QualificationCategory.ADR,
      "Qualification for basic dangerous goods transport.",
      "Qualification required for drivers transporting dangerous goods under the general ADR"
          + " framework, excluding specialized extensions when required."),

  ADR_TANK(
      "ADR_TANK",
      "ADR Tank",
      QualificationCategory.ADR,
      "Qualification for tank dangerous goods transport.",
      "Qualification required for drivers transporting dangerous goods in tanks, tank containers or"
          + " similar transport units."),

  ADR_CLASS_1(
      "ADR_CLASS_1",
      "ADR Class 1",
      QualificationCategory.ADR,
      "Qualification for explosive dangerous goods transport.",
      "Qualification related to the transport of class 1 dangerous goods, covering explosive"
          + " substances and articles."),

  ADR_CLASS_2(
      "ADR_CLASS_2",
      "ADR Class 2",
      QualificationCategory.ADR,
      "Qualification for gas dangerous goods transport.",
      "Qualification related to the transport of class 2 dangerous goods, covering gases."),

  ADR_CLASS_3(
      "ADR_CLASS_3",
      "ADR Class 3",
      QualificationCategory.ADR,
      "Qualification for flammable liquid dangerous goods transport.",
      "Qualification related to the transport of class 3 dangerous goods, covering flammable"
          + " liquids."),

  ADR_CLASS_4(
      "ADR_CLASS_4",
      "ADR Class 4",
      QualificationCategory.ADR,
      "Qualification for flammable solid dangerous goods transport.",
      "Qualification related to the transport of class 4 dangerous goods, covering flammable solids"
          + " and related materials."),

  ADR_CLASS_5(
      "ADR_CLASS_5",
      "ADR Class 5",
      QualificationCategory.ADR,
      "Qualification for oxidizing dangerous goods transport.",
      "Qualification related to the transport of class 5 dangerous goods, covering oxidizing"
          + " substances and organic peroxides."),

  ADR_CLASS_6(
      "ADR_CLASS_6",
      "ADR Class 6",
      QualificationCategory.ADR,
      "Qualification for toxic and infectious dangerous goods transport.",
      "Qualification related to the transport of class 6 dangerous goods, covering toxic and"
          + " infectious substances."),

  ADR_CLASS_7(
      "ADR_CLASS_7",
      "ADR Class 7",
      QualificationCategory.ADR,
      "Qualification for radioactive dangerous goods transport.",
      "Qualification related to the transport of class 7 dangerous goods, covering radioactive"
          + " material."),

  ADR_CLASS_8(
      "ADR_CLASS_8",
      "ADR Class 8",
      QualificationCategory.ADR,
      "Qualification for corrosive dangerous goods transport.",
      "Qualification related to the transport of class 8 dangerous goods, covering corrosive"
          + " substances."),

  ADR_CLASS_9(
      "ADR_CLASS_9",
      "ADR Class 9",
      QualificationCategory.ADR,
      "Qualification for miscellaneous dangerous goods transport.",
      "Qualification related to the transport of class 9 dangerous goods, covering miscellaneous"
          + " dangerous substances and articles."),

  ATP(
      "ATP",
      "ATP",
      QualificationCategory.FOOD_PHARMACEUTICALS,
      "Certification for refrigerated transport vehicles.",
      "Certification related to vehicles and equipment used for temperature-controlled transport of"
          + " perishable food products."),

  HACCP(
      "HACCP",
      "HACCP",
      QualificationCategory.FOOD_PHARMACEUTICALS,
      "Certification for food handling procedures.",
      "Training and certification related to hygiene, food safety and handling procedures in food"
          + " transport and logistics."),

  PHARMACEUTICAL_TRANSPORT(
      "PHARMACEUTICAL_TRANSPORT",
      "Pharmaceutical Transport",
      QualificationCategory.FOOD_PHARMACEUTICALS,
      "Certification for pharmaceutical transport.",
      "Certification or qualification related to the transport of pharmaceutical and healthcare"
          + " products, including temperature-sensitive goods."),

  LIVE_ANIMALS(
      "LIVE_ANIMALS",
      "Live Animals",
      QualificationCategory.ANIMALS,
      "Qualification for live animal transport.",
      "Qualification required for transport activities involving live animals and related"
          + " operational handling requirements."),

  ANIMAL_WELFARE(
      "ANIMAL_WELFARE",
      "Animal Welfare",
      QualificationCategory.ANIMALS,
      "Qualification for animal welfare during transport.",
      "Training related to animal welfare standards, handling procedures and transport conditions"
          + " for animals."),

  SLAUGHTER_ANIMALS(
      "SLAUGHTER_ANIMALS",
      "Slaughter Animals",
      QualificationCategory.ANIMALS,
      "Qualification for slaughter animal transport.",
      "Qualification related to the transport of animals intended for slaughter, including specific"
          + " welfare and handling requirements."),

  PET_ANIMALS(
      "PET_ANIMALS",
      "Pet Animals",
      QualificationCategory.ANIMALS,
      "Qualification for companion animal transport.",
      "Qualification related to the transport of companion animals and domestic pets under"
          + " controlled operational conditions."),

  WASTE_CATEGORY_1(
      "WASTE_CATEGORY_1",
      "Waste Category 1",
      QualificationCategory.WASTE,
      "Authorization for category 1 waste transport.",
      "Authorization related to waste transport operations classified under waste category 1."),

  WASTE_CATEGORY_2(
      "WASTE_CATEGORY_2",
      "Waste Category 2",
      QualificationCategory.WASTE,
      "Authorization for category 2 waste transport.",
      "Authorization related to waste transport operations classified under waste category 2."),

  WASTE_CATEGORY_3(
      "WASTE_CATEGORY_3",
      "Waste Category 3",
      QualificationCategory.WASTE,
      "Authorization for category 3 waste transport.",
      "Authorization related to waste transport operations classified under waste category 3."),

  WASTE_CATEGORY_4(
      "WASTE_CATEGORY_4",
      "Waste Category 4",
      QualificationCategory.WASTE,
      "Authorization for category 4 waste transport.",
      "Authorization related to waste transport operations classified under waste category 4."),

  WASTE_CATEGORY_5(
      "WASTE_CATEGORY_5",
      "Waste Category 5",
      QualificationCategory.WASTE,
      "Authorization for category 5 waste transport.",
      "Authorization related to waste transport operations classified under waste category 5."),

  WASTE_CATEGORY_6(
      "WASTE_CATEGORY_6",
      "Waste Category 6",
      QualificationCategory.WASTE,
      "Authorization for category 6 waste transport.",
      "Authorization related to waste transport operations classified under waste category 6."),

  WASTE_CATEGORY_8(
      "WASTE_CATEGORY_8",
      "Waste Category 8",
      QualificationCategory.WASTE,
      "Authorization for category 8 waste transport.",
      "Authorization related to waste transport operations classified under waste category 8."),

  WASTE_CATEGORY_9(
      "WASTE_CATEGORY_9",
      "Waste Category 9",
      QualificationCategory.WASTE,
      "Authorization for category 9 waste transport.",
      "Authorization related to waste transport operations classified under waste category 9."),

  WASTE_CATEGORY_10(
      "WASTE_CATEGORY_10",
      "Waste Category 10",
      QualificationCategory.WASTE,
      "Authorization for category 10 waste transport.",
      "Authorization related to waste transport operations classified under waste category 10."),

  FORKLIFT(
      "FORKLIFT",
      "Forklift",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for operation of forklifts.",
      "Qualification required for operators using forklifts and similar warehouse handling"
          + " equipment."),

  MEWP(
      "MEWP",
      "MEWP",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for operation of mobile elevating work platforms.",
      "Qualification required for operators using mobile elevating work platforms in industrial,"
          + " logistics or construction environments."),

  TRUCK_MOUNTED_CRANE(
      "TRUCK_MOUNTED_CRANE",
      "Truck Mounted Crane",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for operation of truck mounted cranes.",
      "Qualification required for operators using cranes installed on trucks for loading, unloading"
          + " and lifting operations."),

  TOWER_CRANE(
      "TOWER_CRANE",
      "Tower Crane",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for operation of tower cranes.",
      "Qualification required for operators using tower cranes in construction and industrial"
          + " environments."),

  EXCAVATOR(
      "EXCAVATOR",
      "Excavator",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for operation of excavators.",
      "Qualification required for operators using excavators for earthmoving and construction"
          + " activities."),

  WHEEL_LOADER(
      "WHEEL_LOADER",
      "Wheel Loader",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for operation of wheel loaders.",
      "Qualification required for operators using wheel loaders for loading, handling and"
          + " earthmoving operations."),

  BACKHOE_LOADER(
      "BACKHOE_LOADER",
      "Backhoe Loader",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for operation of backhoe loaders.",
      "Qualification required for operators using backhoe loaders for excavation, loading and"
          + " construction activities."),

  TELEHANDLER(
      "TELEHANDLER",
      "Telehandler",
      QualificationCategory.MACHINE_OPERATORS,
      "Qualification for operation of telehandlers.",
      "Qualification required for operators using telescopic handlers for lifting, loading and"
          + " material handling operations."),

  CONSTRUCTION_SITE_SAFETY(
      "CONSTRUCTION_SITE_SAFETY",
      "Construction Site Safety",
      QualificationCategory.SAFETY,
      "Training for construction site safety procedures.",
      "Training required to operate safely in construction sites, yards and work areas with"
          + " specific operational risks."),

  PPE_CATEGORY_III(
      "PPE_CATEGORY_III",
      "PPE Category III",
      QualificationCategory.SAFETY,
      "Training for category III PPE safety procedures.",
      "Training required for the correct use of category III personal protective equipment in"
          + " high-risk work activities."),

  ROAD_SIGNAGE(
      "ROAD_SIGNAGE",
      "Road Signage",
      QualificationCategory.SAFETY,
      "Training for road signage safety procedures.",
      "Training required for activities involving road signage, roadside works and traffic-related"
          + " operational safety."),

  LOAD_HANDLING(
      "LOAD_HANDLING",
      "Load Handling",
      QualificationCategory.SAFETY,
      "Training for load handling safety procedures.",
      "Training related to safe load handling, manual handling and movement of goods in operational"
          + " environments."),

  FIRE_SAFETY(
      "FIRE_SAFETY",
      "Fire Safety",
      QualificationCategory.SAFETY,
      "Training for fire safety procedures.",
      "Training required for fire prevention, emergency response and workplace fire safety"
          + " duties."),

  FIRST_AID(
      "FIRST_AID",
      "First Aid",
      QualificationCategory.SAFETY,
      "Training for first aid safety procedures.",
      "Training required for workplace first aid duties and emergency assistance procedures."),

  CONTAINER_TRANSPORT(
      "CONTAINER_TRANSPORT",
      "Container Transport",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for container port operations.",
      "Qualification related to container transport and handling in ports, terminals and intermodal"
          + " logistics areas."),

  IMO(
      "IMO",
      "IMO",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for IMO port operations.",
      "Qualification related to maritime dangerous goods handling and port operational"
          + " requirements."),

  PORT_AREA_ACCESS(
      "PORT_AREA_ACCESS",
      "Port Area Access",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for port area operations.",
      "Permit or qualification required to access and operate within port areas and terminal"
          + " facilities."),

  PORT_ADR(
      "PORT_ADR",
      "Port ADR",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for ADR port operations.",
      "Qualification related to dangerous goods transport and handling operations in port"
          + " environments."),

  AIRPORT_CARGO(
      "AIRPORT_CARGO",
      "Airport Cargo",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for airport cargo operations.",
      "Qualification required to operate in airport cargo environments and regulated air freight"
          + " logistics areas."),

  AIRPORT_SECURITY(
      "AIRPORT_SECURITY",
      "Airport Security",
      QualificationCategory.PORTS_AND_AIRPORTS,
      "Qualification for airport security operations.",
      "Training related to airport security requirements, access control and regulated airport"
          + " operations."),

  LOGISTICS(
      "LOGISTICS",
      "Logistics",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for logistics operations.",
      "Internal company training related to logistics processes, transport coordination and"
          + " operational workflows."),

  WAREHOUSE_MANAGEMENT(
      "WAREHOUSE_MANAGEMENT",
      "Warehouse Management",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for warehouse management.",
      "Internal company training related to warehouse organization, storage processes and"
          + " operational management."),

  LOAD_SLINGING(
      "LOAD_SLINGING",
      "Load Slinging",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for load slinging.",
      "Internal company training related to load slinging, securing and preparation for lifting or"
          + " handling activities."),

  RAMPS_AND_DOCKS(
      "RAMPS_AND_DOCKS",
      "Ramps and Docks",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for ramps and docks.",
      "Internal company training related to the safe use of loading ramps, docks, bays and loading"
          + " areas."),

  SENSITIVE_GOODS_TRANSPORT(
      "SENSITIVE_GOODS_TRANSPORT",
      "Sensitive Goods Transport",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for sensitive goods transport.",
      "Internal company training related to handling, loading and transport of sensitive or"
          + " high-value goods."),

  MEDICAL_EQUIPMENT_TRANSPORT(
      "MEDICAL_EQUIPMENT_TRANSPORT",
      "Medical Equipment Transport",
      QualificationCategory.COMPANY_LOGISTICS,
      "Internal training for medical equipment transport.",
      "Internal company training related to transport, handling and protection of medical"
          + " equipment.");

  private final String code;
  private final String displayName;
  private final QualificationCategory category;
  private final String shortDescription;
  private final String longDescription;

  Qualification(
      String code,
      String displayName,
      QualificationCategory category,
      String shortDescription,
      String longDescription) {
    this.code = code;
    this.displayName = displayName;
    this.category = category;
    this.shortDescription = shortDescription;
    this.longDescription = longDescription;
  }

  public String code() {
    return code;
  }

  public String displayName() {
    return displayName;
  }

  public QualificationCategory category() {
    return category;
  }

  public String shortDescription() {
    return shortDescription;
  }

  public String longDescription() {
    return longDescription;
  }
}
