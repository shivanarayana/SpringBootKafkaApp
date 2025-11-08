Feature: Infrastructure Readiness Check

  Scenario: Verify Kafka Broker Connection
    Given the TestManager has initialized
    When the ServiceChecker runs
    Then the Kafka component status should be "UP"
    And the Camunda component status should be "DOWN"