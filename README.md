Build with gradle5:
gradle build -x

Purpose:
Detect crashed pods in aws kube cluster

Run in console
java -jar ./build/libs/aws_kubernets_checker-all.jar

Output is :
Terminated pod ===parser-staging-5bbccb8fb8-fz74w
pod name=web-staging-5bbccb8fb8-fz74w
Terminated pod ===web-staging-5bbccb8fb8-fz74w
pod name=web-staging-5bbccb8fb8-fz74w
Terminated pod ===web-staging-5bd7ff858f-kp68f
Pod  staging-airflow-web-6b95f5f7b9-xjxn4   isn't running

tool shows in console pods which have problems

