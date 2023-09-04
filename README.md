# Shipping discount module with REST

### Requirements
###### openJDK 17
###### Gradle 7.5.1

The rule for 'Using additional libraries is prohibited' was broken by choosing Spring, but I wanted to have a functioning API with a database configuration for ease of use.

### Run module
``java -jar ./theJarPlace/shipping.jar``

### Endpoints

#### Outputs the discount result in the response body
``curl -F file=@path/to/input.txt http://localhost:8080/transaction``

## Unit tests added for Input reading and Transaction validation service classes

