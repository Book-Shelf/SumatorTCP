# TCPAdder

TCPAdder is a TCP adding protocol which task is to sum numbers given by a client.

## Specificaton

Client sends one or more number seqences. For every sequence server returns calculated sum or error. Sequence is equal to line - sequance of printable ASCII signs ended with "\r\n".

Line can contain digits and spaces. Space acts as a number separator. Each space has to be between two digits. Line cannot be empty, thus has to contain at least one number. 

If sum cannot be calculated an "ERROR\r\n" message has to be returned. If overflow occurs, an "ERROR\r\n" message has to be returned.

## Starting the server

Use Main class to start a server. Server is listening on port 2020.
```java
$java Main
```

## Example

```bash
1 2 3\r\n4 5 6\r\n
6\r\n
15\r\n
```

```bash
 3\r\n4 5 6\r\n2 r 3\r\n
ERROR\r\n
15\r\n
ERROR\r\n
```

