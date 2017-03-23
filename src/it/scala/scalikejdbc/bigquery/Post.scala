package scalikejdbc.bigquery

import java.time.{ZoneId, Instant, ZonedDateTime}

import scalikejdbc._

case class PostId(value: Int) extends AnyVal

case class Post(
  id: PostId,
  body: String,
  postedAt: ZonedDateTime
)

case class PostWithTags(
  post: Post,
  tags: Seq[Tag]
)

object Post extends SQLSyntaxSupport[Post] {
  override val columns = Seq("id", "body", "posted_at")

  implicit val postIdBinders: Binders[PostId] = Binders.int.xmap(PostId.apply, _.value)
  implicit val zonedDateTimeTypeBinder: TypeBinder[ZonedDateTime] =
    TypeBinder.jodaDateTime.map(t => ZonedDateTime.ofInstant(Instant.ofEpochMilli(t.getMillis), ZoneId.of("UTC")))

  val p = this.syntax("p")

  def apply(rs: WrappedResultSet): Post = apply(rs, p.resultName)
  def apply(rs: WrappedResultSet, rn: ResultName[Post]): Post = new Post(
    id = rs.get[PostId](rn.id),
    body = rs.get[String](rn.body),
    postedAt = rs.get[ZonedDateTime](rn.postedAt))
}
