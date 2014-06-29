package org.http4s.rho
package bits

import org.specs2.mutable.Specification
import scodec.bits.ByteVector
import org.http4s._
import org.http4s.rho.RhoService
import org.http4s.Status._

/**
 * Created by Bryce Anderson on 5/4/14.
 */
class HListToFuncTest extends Specification {

  def getBody(b: HttpBody): String = {
    new String(b.runLog.run.foldLeft(ByteVector.empty)(_ ++ _).toArray)
  }

  def checkOk(r: Request): String = getBody(service(r).run.body)

  def Get(s: String, h: Header*): Request = Request(GET, Uri.fromString(s).get, headers = Headers(h:_*))

  val service = new RhoService {
    GET / "route1" !> { () => Ok("foo") }
    GET / "route2" !> { () => "foo" }
  }

  "HListToFunc" should {
    "Work for methods of type _ => Task[Response]" in {
      val req = Get("/route1")
      checkOk(req) should_== "foo"
    }

    "Work for methods of type _ => O where a Writable[O] exists" in {
      val req = Get("/route2")
      checkOk(req) should_== "foo"
    }
  }
}
