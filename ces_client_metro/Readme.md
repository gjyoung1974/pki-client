# "Certificate Enrollment Web Service" for MSFT SCEP

The Main() class will 
1. Generate a new CSR
2. submit request to CA
3. retrieve signed certificate and save to file as:
{project path}/cert.cer

Change the value of:
MEXuRI
to point at a different CA.

Change the value of:
TemplateName
To use a different certificate template name

Change the value of:
SubjectDN
To use a different subject DN name

files:
{project path}/cacerts contains the truststore for authentication to the MSCA web service
{project path}/svc-ces_client.pfx contains the PKI Service Account credential
{project path}/lib contains various web service libraries required to communicate with the CA

This client requires JRE 6 or better

---
gjyoung1974@gmail.com
