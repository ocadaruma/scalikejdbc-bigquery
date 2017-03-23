package scalikejdbc.bigquery

import scalikejdbc._

case class TagId(value: Int) extends AnyVal

case class Tag(
  id: TagId,
  postId: PostId,
  name: String
)

object Tag extends SQLSyntaxSupport[Tag] {
  override val columns = Seq("id", "post_id", "name")

  import Post.postIdBinders

  implicit val tagIdBinders: Binders[TagId] = Binders.int.xmap(TagId.apply, _.value)

  val t = this.syntax("t")

  def apply(rs: WrappedResultSet): Tag = new Tag(
    id = rs.get[TagId](t.resultName.id),
    postId = rs.get[PostId](t.resultName.postId),
    name = rs.get[String](t.resultName.name)
  )
  def opt(rs: WrappedResultSet): Option[Tag] = rs.intOpt(t.resultName.id).map(_ => apply(rs))
}
