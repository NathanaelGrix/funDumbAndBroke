(ns fdab.core
  (:import 
    [java.net Socket ServerSocket]
    [java.io InputStream OutputStream InputStreamReader PrintWriter BufferedReader]))

; Sets up the server and returns the input and output buffers
(defn connect-client [] 
  (def server (ServerSocket. 5050))
  (println "socket set up")
  (def client (.accept server))
  (println "Connection recieved")
  (def clin (BufferedReader. (InputStreamReader. (.getInputStream client))))
  (def clot (PrintWriter. (.getOutputStream client) true))
  [clin clot])

; Gets the client to connect to the server and returns the input and output buffers
(defn connect-server [] 
  (println "starting client")
  (def socket (Socket. "localhost" 5050))
  (println socket)
  (def socin (BufferedReader. (InputStreamReader. (.getInputStream socket))))
  (def socot (PrintWriter. (.getOutputStream socket) true))
  [socin socot])

; Prints out all accumulated messages
(defn getmsg [msgin] 
  (println "them:")
  (while (.ready msgin) (println \tab (.readLine msgin)))
  (println ""))

; Sends a message 
(defn sendmsg [msgot]
  (.println msgot (read-line)))

; Loops through sending something then recieving everything
(defn send-recieve-loop [msgin msgot]
  (println "send-recieve-loop")
  (while true 
    (do 
      (sendmsg msgot)
      (getmsg msgin))))

; Asks if we are hosting the connection and if we are sets up the server else it connects to it
(defn -main [] 
  (println "host or not host?")
  (def in-out (if (= (read-line) "host") (connect-client) (connect-server)))
  (send-recieve-loop (get in-out 0) (get in-out 1)))
