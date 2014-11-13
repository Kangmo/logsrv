package controllers

import play.api._
import play.api.mvc._

import java.io._
import play.api.libs.iteratee._
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {
	def version = Action {
		Ok("""{"version"="1.0.0"}""")
	}
/*
	def acceptLog(key:String, location:String) = Action { implicit request =>
		println(s"key:$key, location:$location, request : " + request.body)
		Ok("success")
	}
*/

/*
 	def acceptLog(key:String, location:String) = Action( parse.multipartFormData(myPartHandler) ) { implicit request =>
        Ok("Done")
 	}


	private def myPartHandler: BodyParsers.parse.Multipart.PartHandler[MultipartFormData.FilePart[Result]] = {
        parse.Multipart.handleFilePart {
          case parse.Multipart.FileInfo(partName, filename, contentType) =>
            //Still dirty: the path of the file is in the partName...
            val path:String = partName;

            //Set up the PipedOutputStream here, give the input stream to a worker thread
            val pos:PipedOutputStream = new PipedOutputStream();
            val pis:PipedInputStream  = new PipedInputStream(pos);
            val worker:LogStorageWorker = new LogStorageWorker(path,pis);
            worker.contentType = contentType.get;
            worker.start();

            //Read content to the POS
            Iteratee.fold[Array[Byte], PipedOutputStream](pos) { (os, data) =>
              os.write(data)
              os
            }.map { os =>
              os.close()
              Ok("upload done")
            }
        }
   } 
*/


	// source from https://groups.google.com/forum/#!topic/play-framework/MWzq7saY0JA
	def bodyParser(key:String, location:String) = BodyParser { request => 
		Iteratee.fold[Array[Byte],Int](0) { (c, rawData) => 
			val data = new String(rawData, java.nio.charset.StandardCharsets.UTF_8)
//			println(s"c=$c; got ${data.length} bytes (${new String(data).substring(0, len)}...)"); 
			import play.api.Logger._
			info(data)
			c+1 
		}.map(Right(_)) 
	} 


	// Upload with this commands
	// curl -X PUT -d payload http://localhost/key/hosts/location/
	// curl -X PUT -d @netty-3.9.3.Final-dist.tar.bz2 http://localhost/key/hosts/location/
	def acceptLog(key:String, location:String) = Action(bodyParser(key,location)) { request : Request[Int] =>
		// Use req.body in the same way as documented in the above docs, the types of the file parts in this case will be Unit
		Ok("got " + request.body + " chunks") 
	}

/*
	def acceptLog(key:String, location:String) = WebSocket.using[String] { request =>

	  // Log events to the console
	  val in = Iteratee.foreach[String](println).map { _ =>
	    println("Disconnected")
	  }

	  // Send a single 'Hello!' message
	  val out = Enumerator("Hello!")

	  (in, out)
	}
*/
}