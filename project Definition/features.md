# features
## implement a charging station with multiple locations:
- Develop simulation of the **Car charging station** with **several alternative sources of energy**
- Several car charging stations are available with **n location** in it.
### we have a charging station that have:
- several alternative sources of energy (solar, wind, water, etc.)
- several charging locations

### we have the following rules:
- each charging location can accommodate only one car at a time
- the time required to charge one car is fixed
- if all places are occupied, the car will not wait for more than a certain time and will go to another car charging station
-----
## Occupancy Handling in Charging Station:
Implement a system that checks if all charging locations are occupied.
If all places are occupied, simulate a scenario where a car will not wait for more than a certain time.
-----
## going to another charging station:
- Define a mechanism for a car to go to another charging station if it cannot wait.
-----
## Weather Conditions Simulation:
Integrate a weather simulation component to influence the charging station's situation.
Different weather conditions might affect the charging speed or availability of certain energy sources.
As a simulation user, I want to observe how different weather conditions affect the charging station, such as charging speed or energy source availability.
-----
## Energy Management System in Charging Station:
Develop a management system for the energy sources.
Implement logic to determine which energy source to use based on the weather.
Simulate scenarios where the charging station might prioritize one source over another.
-----
## Logging System:
Implement a logging system to record the charging station's state, weather conditions, and energy source usage.
Log important events, such as when a car arrives, starts charging, leaves, or switches charging locations.
-----
