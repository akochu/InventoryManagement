Inventory Management System
---------------------------

Note: The compiled jar is out/artifacts/InventoryManagement_jar/InventoryManegement.jar and can
be executed as 'java -jar InventoryManegement.jar'.

This Inventory Management system has Producer-Consumer pattern implemented, to have input streams
interact with existing inventory of items A, B, C, D, E, and print a report of the orders completed
and back-ordered.

The InventoryManager and OrderTracker is implemented as Singleton objects, to ensure
that there are only one instances of these objects being shared by all the threads. The InventoryManager
is initiated with a bunch of product names and quantities, in the 'inventory' object. The OrderTracker
similarly maintains a datastructure 'ordersPlaced', where evey order is inserted as an OrderTrackingEntry.

Once the inventory items drop to zero, the report is generated from the OrderTrackingEntry objects in the
OrderTracker. This datastructure retains the order, thus the entries are printed out in the order, that
they are inserted.

OrderBroker
-----------
- OrderBroker maintains a queue of Orders, thus maintaining the order of Orders recieved. The Producer
and Consumer threads interact with this Broker object, to insert and consume Order objects into the
queue.
- This object interacts with the InventoryManager to adjust the quantities of the products in the
inventories, according to the orders recieved in the Consumer threads.
- OrderBroker inserts OrderTrackingEntry objects into the 'ordersPlaced' in the OrderTracker singleton.

OrderProducer
-------------
The Order Producer produces streams of input, which consist of instances of the model object Order.
These are inserted into the queue by the OrderBroker and consumed by Consumer.

Consumer
---------
The OrderBroker gets Order objects off the queue, for the Consumer threads.

References:-
https://dzone.com/articles/concurrency-pattern-producer
