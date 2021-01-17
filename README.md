**Requirements**

Dispatch++ team is building a Call For Service (CFS) management service with the following requirements 
1. Dispatcher should be able to create a CFS with the following information: 
event number, event type (with type code), event time, dispatch time, responder. 
2. Dispatcher should be able to search for CFS within a time range with paging and sorting order. 
3. Dispatcher should be able to search for CFS that assigned to a responder. 
4. System administrator should be able to import the list of CFS from external system in CSV format. 
5. CFS belongs to different agencies are not allowed to be exposed to other agencies. 
6. Dispatcher and responder should belong to only one agency. 
7. CFS service is expected to have 99.99% up time so we need to have a set of monitoring metrics for this service. 

**Technical Design**
- Technical stack: Spring Boot, Spring Security, Spring JPA Data, Embedded H2 Database
- Please see technical design in folder: /uml

**Set Up**
- Required dependences: maven 3.5, java 8
- run `mvn clean install` to build the project
- For dev mode: Create spring boot Application and run server
After lauching, the server will go live at **8091** port. You can change port by changing the property `server.port` in `application.properties`

Due to lack of time, I can only provide some basic unit test cases.

**API guideline**
- I support a simple authentication method using bearer token. I also generated some seed officer data in `changelog-202101161011.xml`,
you can go through this file to find the appropriate access token for authentication.

- I prepare some basic cases for those requirements by postman, you can see the sample API collection below:
https://www.getpostman.com/collections/f61290d0aa28648d1866

**Req 7, for monitoring service:** 

1/ If we can use AWS, use CloudWatch for creating alarms, scaling up/down our systems, some metrics such as: 
- alert to email/slack channel: database connections: active connections, CPU, memory, disk, network data...
- scale up/down server: network data, throughput,...

2/ We can use some external monitoring services (e.g. sumologic) to parse log messages and build some custom dashboard and alert, such as:
- logged the number of failed/success items when importing csv and build a dashboard for import csv: number of importing request, number of failed items, number of success items
- alert when the number of exception log is over a config amount

3/ For easier monitoring and troubleshoot issue, I suggest a simple logback design like below:

- Add a new response header for `trace_id` in each request, header name: call-request-trace-id.
- The header value (aka `trace_id`) contains an UUID number without "-" character (for easier for searching in some monitoring service UIs, we do not include double quote for searching with exact mode)
- Include `trace_id` in each logger message. I configure logback to include trace_id, dispatcher_no, dispatcher_agency_id for tracing log
`<property name="LOG_MESSAGE_PATTERN" value="%d [%t] %p %c{1} - %m - %X{executionId}/%X{dispatcher}/%X{agency}%n" />`
- `trace_id` for searching log messages, `dispatcher_no, agency_id` to identify who executing the request. Because the dispatcher can be moved to another agency, the `agency_id` to check the current agency of dispatcher when he/she executes the API.
- `trace_id` will be reset in beginning of the request

With those configurations, when I execute an API, copy the value of response header `call-request-trace-id`, search by this value, it will list all the log message regarding to this API.

For ex, I tried to execute import an csv file:

1/ Check the response header `call-request-trace-id:491d271b5bf7496c9eec76603fd04305`
 
2/ Then copy `trace_id` and search in the log file or monitoring service, we will receive all the messages like this:

`2021-01-18 18:57:24,527 [http-nio-8091-exec-3] DEBUG c.e.d.c.OfficerAuthenticationFilter - Request is to process authentication - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9
 2021-01-18 18:57:24,529 [http-nio-8091-exec-3] DEBUG c.e.d.c.OfficerAuthenticationFilter - Request is to process authentication - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9
 2021-01-18 18:57:24,538 [http-nio-8091-exec-3] INFO c.e.d.s.EventRequestService - Import file is starting... - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9
 2021-01-18 18:57:24,541 [http-nio-8091-exec-3] ERROR c.e.d.s.EventRequestService - Error while importing line 1 - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9
 2021-01-18 18:57:24,542 [http-nio-8091-exec-3] ERROR c.e.d.s.EventRequestService - Error while importing line 2 - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9
 2021-01-18 18:57:24,546 [http-nio-8091-exec-3] ERROR c.e.d.s.EventRequestService - Error while importing line 4 - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9
 2021-01-18 18:57:24,550 [http-nio-8091-exec-3] ERROR c.e.d.s.EventRequestService - Error while importing line 6 - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9
 2021-01-18 18:57:24,551 [http-nio-8091-exec-3] ERROR c.e.d.s.EventRequestService - Error while importing line 7 - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9
 2021-01-18 18:57:24,552 [http-nio-8091-exec-3] ERROR c.e.d.s.EventRequestService - Error while importing line 8 - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9
 2021-01-18 18:57:24,552 [http-nio-8091-exec-3] INFO c.e.d.s.EventRequestService - Import file completed, success items = 2, failed items = 6 - 491d271b5bf7496c9eec76603fd04305/OFFICER_002/4f9b99eb-490a-484e-bade-15e3841dfda9`

  	