# Task 3 for compact programming course - java

## team members:

1. Ahmed Hobeishy (7219053, MDT)
2. Varsha Vijayan (7216653 ,MDT)
3. Silpa Prasad Sivaprasad (7216655 ,MDT)

## team responsibilities for task 3:

1. `Car + ChargingCar + charging Logic in the ChargingStation` / Task: Simulate the system which allow to charge 
   simultaneously 1..N vehicles, depending on the available resources.
    - Silpa Prasad Sivaprasad

2. `StationWaitingQueue + queue managment logic in the ChargingStation` / Task: car arriving in random moment of time.
   When the car appear in the queue should be calculated the waiting time, if time is more then 15 min car leaving the
   queue.
    - Varsha Vijayan

3. `EnergySource + ReservedBattery + EnergySourceType` / Task: Simulate multithread charging of the reserved batteries
   from several energy sources + test the entire application.
    - Ahmed Hobeishy

## our solution (you can check the output in the logs folder):

- if you run the main method:
- 2 charging stations will be created with 2 charging points each
    - every station will have a waiting queue
        - cars will be added randomly to the waiting queue of a station, if it can't be added to the queue, it will be
          added to the other station

- for the energy sources and battery:
    - ever station will have 3 energy sources, a thread will run every 30 seconds to check the weather and see if the
      energy source is will be working or not
    - every energy source will have a thread that will run every 5 seconds and if the source is working, it will charge
      the battery

- every station has its own charging thread, so multiple station will be working on parallel each with its own charging
  point and queue

Note:
we removed the logic from the previous 2 task to focus on the logic of the new task,
for the capstone project we will review the 3 tasks, merge them and remove any logic that is not needed anymore or add logic that is needed for the  complete project.

## Environment:
- Eclipse IDE
- Java 17
