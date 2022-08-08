# The Challenge

The goal of this challenge is to build a web service that exposes Vehicle (Bus), Fleet (Operator) and Activity (Stop) data, for a given time frame.  

The service exposes a RESTful API to answer the following questions:
1. Given a time frame [start-time, end-time], what is the list of running operators?
2. Given a time frame [start-time, end-time] and an Operator, what is the list of vehicle IDs?
3. Given a time frame [start-time, end-time] and a fleet, which vehicles are at a stop?
4. Given a time frame [start-time, end-time] and a vehicle, return the trace of that vehicle (GPS entries, ordered by timestamp).

Before implementing this exercise, you need to:

- Choose a database management system that is more appropriate for building this web service (we value NoSQL databases, but a relational database may be also fine);
- Choose a programming language that is type-safe and you are familiar with;
- Choose a web toolkit that is able to handle high throughput in terms of number of requests (we are less worried about latency, but that's not to be neglected as well).

## What to deliver
- Instructions on how to install and/or access to the database;
- A data loader script;
- The code of the web service; 
- Instructions on how to launch the HTTP service;
- Documentation and/or examples on how to use and/or test the API.

The implementation **MUST** be delivered using this repo. Other files (e.g. large files or csv data) **MAY** be outside.  

You have one week to deliver the solution.

## Dataset to use

You may find the datasets here:  
Name: Dublin Bus GPS sample data from Dublin City Council  
URL:  https://codechallengestracc.blob.core.windows.net/code-challenge/dublin-dataset.zip  
URL: https://data.gov.ie/dataset/dublin-bus-gps-sample-data-from-dublin-city-council-insight-project

Download one dataset, extract, and from that extraction use 1 CSV example file as input.  

Note: This data is public and not owned by us, and may be subject to restrictions and limitations as described by the Dublin City Council, in the dataset's own license section. For more details on the dataset and data types contained therein, you can check the description on the website.

## Contribution Model

For any questions or clarifications, you should engage with the reviewers that were added as collaborators to this repository. We leave it to you to decide the best way to engage with them.  
When your implementation is ready-for-review, let them know so that they can take a look and provide feedback.

Good Luck 🍀 and have fun!