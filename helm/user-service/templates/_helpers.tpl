{{/*
Return the name of the chart
*/}}
{{- define "user-service.name" -}}
{{ .Chart.Name | trunc 63 | trimSuffix "-" }}
{{- end -}}
