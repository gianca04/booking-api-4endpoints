@BookingAll
Feature: APIS from Booking

  Como usuario de Booking
  Quiero listar, crear, autenticarme y actualizar bookings
  Para verificar que los 4 endpoints responden correctamente

  @CP01_Booking @Booking
  Scenario: Obtener todos los booking exitosamente
    Given el actor establece el endpoint de booking
    When el actor realiza una solicitud GET
    Then el codigo de respuesta debe ser 200

  @CP02_Booking @Booking
  Scenario Outline: Crear un booking exitosamente
    Given el actor establece el endpoint de booking
    When el actor crea un booking con el "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Mario     | Castilla | 111        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |

  @CP03_Booking @Booking
  Scenario: Generar un token exitosamente
    Given el actor establece el endpoint de booking
    When el actor genera un token con "admin" "password123"
    Then el codigo de respuesta debe ser 200

  @CP04_Booking @Booking
  Scenario Outline: Actualizar datos de un booking exitosamente
    Given el actor establece el endpoint de booking
    And el actor crea un booking con el "Ada" "Wong" "111" "true" "2018-01-01" "2019-01-01" "Run"
    And el actor genera un token con "admin" "password123"
    When el actor actualiza un booking con los datos "<firstname>" "<lastname>" "<totalprice>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | firstname | lastname  | totalprice |
      | Carlos    | Alcantara | 999        |
