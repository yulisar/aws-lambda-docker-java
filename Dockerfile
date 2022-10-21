FROM public.ecr.aws/lambda/java:11

# Copy function code and runtime dependencies from Gradle layout
# COPY build/classes/java/main ${LAMBDA_TASK_ROOT}
# COPY build/dependencies/* ${LAMBDA_TASK_ROOT}/lib/

#COPY target/dependency/* /function/
COPY target/hello-lambda-javaSimple.jar /function

# Set the CMD to your handler (could also be done as a parameter override outside of the Dockerfile)
#ENTRYPOINT [ "/opt/java/openjdk/bin/java", "-cp", "/function/*", "com.amazonaws.services.lambda.runtime.api.client.AWSLambda" ]
CMD [ "ocsb.mp.cloud.basic::handleRequest" ]
