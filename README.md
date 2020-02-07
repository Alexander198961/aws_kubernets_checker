Build with gradle5:
gradle build

Purpose:
Detect crashed pods in aws kube cluster

Run in console:

java -jar ./build/libs/aws_kubernets_checker-all.jar

Output is : </p>

pod name=parser-staging-847fb75857-5pmtr </p>
Terminated pod ===parser-staging-84c7db6467-nhx9d </p>
pod name=parser-staging-84c7db6467-nhx9d </p>
Terminated pod ===parser-staging-84c7db6467-nhx9d </p>
pod name=parser-staging-84c7db6467-nhx9d </p> 
Terminated pod ===parser-staging-86bc45555-75qjj  </p>
pod name=parser-staging-86bc45555-75qjj </p>
Terminated pod ===parser-staging-86bc45555-75qjj </p>
pod name=parser-staging-86bc45555-75qjj </p>
Pod  staging-airflow-web-6b95f5f7b9-xjxn4   isn't running . </p>

tool shows in console pods which have problems

