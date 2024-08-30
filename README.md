Application-name = Name of the application

Traceid = each request and response traceid is same when calling same service or one service to another service.

Spanid = Span Id is printed along with Trace Id. Span Id is different every request and response calling one service to another service.

Zipkin-export = By default it is false. If it is true, logs will be exported to the Zipkin server.

**Difference between Traceid and spanId**

Trace: A Trace is a view into a request as it moves through a distributed system. It uniquely identifies a single request as it flows through a distributed system.

Span: A span is a named, timed operation that represents a part of the workflow. It uniquely identifies a single operation or unit of work within a trace.