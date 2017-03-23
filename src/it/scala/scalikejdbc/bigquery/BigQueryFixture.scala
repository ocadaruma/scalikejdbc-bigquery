package scalikejdbc.bigquery

import java.io.FileInputStream

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.bigquery.{BigQueryOptions, BigQuery}

trait BigQueryFixture {

  def projectId(): String = sys.env("GCLOUD_PROJECT")

  def mkBigQuery(): BigQuery = {
    val jsonKeyFileLocation = sys.env("GCLOUD_SERVICE_KEY_LOCATION")
    val credentials = GoogleCredentials.fromStream(new FileInputStream(jsonKeyFileLocation))

    BigQueryOptions.newBuilder()
      .setCredentials(credentials)
      .setProjectId(projectId())
      .build()
      .getService
  }
}
