package connectors

import baseSpec.BaseSpecWithApplication
import models._

import scala.concurrent.Await
import scala.concurrent.duration.Duration



class ApplicationConnectorSpec extends BaseSpecWithApplication{

  private val user: User = User("octocat", "2011-01-25T18:44:36Z", Some("San Francisco"), 6537, 9)

  private val repoNameList: List[String] = List("octocat", "boysenberry-repo-1", "git-consortium", "MIT License", "hello-worId", "Hello-World", "linguist", "MIT License", "octocat.github.io","Spoon-Knife", "test-repo1")

  private val folderFile: FFitems = FFitems("README.md", "file", "README.md", "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/README.md?ref=master", "dbae7e62708b8f5fe7fdc474e4cc27f63cea6a3d")
  private val folderFile2: FFitems = FFitems("READTHIS.md", "file", "READTHIS.md", "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/READTHIS.md?ref=master", "e69de29bb2d1d6434b8b29ae775ad8c2e48c5391")
  private val folderFile3: FFitems = FFitems("_config.yml", "file", "_config.yml", "https://api.github.com/repos/octocat/boysenberry-repo-1/contents/_config.yml?ref=master", "06bbaece1a494835566a68b5032ee3474d92fd65")
  private val folderFileSeq: Seq[FFitems] = Seq(folderFile, folderFile2, folderFile3)

  private val dirFolderFile: FFitems = FFitems("git-linguist", "file", "bin/git-linguist", "https://api.github.com/repos/octocat/linguist/contents/bin/git-linguist?ref=master", "9e525bb5b0719762180d8f961cb75129b88506e8")
  private val dirFolderFile2: FFitems = FFitems("linguist", "file", "bin/linguist", "https://api.github.com/repos/octocat/linguist/contents/bin/linguist?ref=master", "d1d9c306eca3750bff68941932e13b2ffdf5b50c")
  private val dirFolderFileSeq: Seq[FFitems] = Seq(dirFolderFile, dirFolderFile2)

  private val file: File = File("README", "980a0d5f19a64b4b30a87d4206aade58726b60e3", "file", "README", "https://api.github.com/repos/octocat/Hello-World/contents/README?ref=master", "https://raw.githubusercontent.com/octocat/Hello-World/master/README", "SGVsbG8gV29ybGQhCg==\n", "Hello World!\n")

  private val repo: Repository =  Repository("octocat", "Hello-World", false, "https://api.github.com/repos/octocat/Hello-World", "https://github.com/octocat/Hello-World")


//  private val createFile: CreateFile = CreateFile("test file", "test create file method")
//  private val returnOfCreate: ReturnCreatedFile = ReturnCreatedFile(Content("testFile.txt", "testFile.txt", "file"))

  val testApplicationConnector: ApplicationConnector = new ApplicationConnector(ws)

  "ApplicationConnector .get()" should {

    "return a user from the github api" in {
      val url = "https://api.github.com/users/octocat"

      val getResult = testApplicationConnector.get[User](url)

     Await.result(getResult, Duration("5 second")) shouldBe Right(user)

    }

    "not return a user from the github api as username does not exist" in {
      val url = "https://api.github.com/users/meeptot"

      val getResult = testApplicationConnector.get[User](url)

      Await.result(getResult, Duration("5 second")) shouldBe Left(APIError.BadAPIResponse(400, "could not find user"))

    }

  }

  "ApplicationConnector .getUserRepo()" should {

    "return a user's repository names from the github api" in {
      val url = "https://api.github.com/users/octocat/repos"
      val getResult = testApplicationConnector.getUserRepo[Repository](url)

      Await.result(getResult, Duration("5 second")) shouldBe Right(repoNameList)

    }

    "not return a user's repository names from the github api as user does not exist" in {
      val url = "https://api.github.com/users/meeptot/repos"
      val getResult = testApplicationConnector.getUserRepo[Repository](url)

      Await.result(getResult, Duration("5 second")) shouldBe  Left(APIError.BadAPIResponse(400, "could not find any repositories associated with that user"))

    }
  }


  "ApplicationConnector .getRepoContent()" should {

    "return a seq of a user's repos folders and files from the github api" in {
      val url = "https://api.github.com/repos/octocat/boysenberry-repo-1/contents"
      val getResult = testApplicationConnector.getRepoContent[FFitems](url)

      Await.result(getResult, Duration("5 second")) shouldBe Right(folderFileSeq)

    }
    "not return repos folders and files from the github api as repo does not exist" in {
      val url = "https://api.github.com/repos/octocat/no/contents"
      val getResult = testApplicationConnector.getRepoContent[FFitems](url)

      Await.result(getResult, Duration("5 second")) shouldBe Left(APIError.BadAPIResponse(400, "could not find any repository files"))

    }
  }



  "ApplicationConnector .getDirContent()" should {

    "return a seq of a user's repos folders and files in a directory from the github api" in {
      val url = "https://api.github.com/repos/octocat/linguist/contents/bin"
      val getResult = testApplicationConnector.getDirContent[FFitems](url)

      Await.result(getResult, Duration("5 second")) shouldBe Right(dirFolderFileSeq)

    }

    "not return a seq of a user's repos folders and files in a directory from the github api directory does not exist" in {
      val url = "https://api.github.com/repos/octocat/linguist/contents/nope"
      val getResult = testApplicationConnector.getDirContent[FFitems](url)

      Await.result(getResult, Duration("5 second")) shouldBe Left(APIError.BadAPIResponse(400, "could not find any repository files"))

    }
  }

  "ApplicationConnector .getFileContent()" should {

    "return a file from a user's repos from the github api" in {
      val url = "https://api.github.com/repos/octocat/Hello-World/contents/README"
      val getResult = testApplicationConnector.getFileContent[File](url)

      Await.result(getResult, Duration("5 second")) shouldBe Right(file)

    }

    "not return a file from a user's repos from the github api as file does not exist" in {
      val url = "https://api.github.com/repos/octocat/Hello-World/contents/nope"
      val getResult = testApplicationConnector.getFileContent[File](url)

      Await.result(getResult, Duration("5 second")) shouldBe Left(APIError.BadAPIResponse(400, "could not find any file contents"))

    }
  }


  "ApplicationConnector .getUserRepoInfo()" should {


    "return a user's repository from the github api" in {
      val url = "https://api.github.com/repos/octocat/Hello-World"
      val getResult = testApplicationConnector.getUserRepoInfo[Repository](url)

      Await.result(getResult, Duration("5 second")) shouldBe Right(repo)

    }

    "not return a user's repository from the github api as repo does not exist" in {
      val url = "https://api.github.com/repos/octocat/stillNope"
      val getResult = testApplicationConnector.getUserRepoInfo[Repository](url)

      Await.result(getResult, Duration("5 second")) shouldBe Left(APIError.BadAPIResponse(400, "could not find any repositories information"))

    }
  }

//  "ApplicationConnector .createFile() and .deleteFile()" should {
//    val url = "https://api.github.com/repos/robyngar/git_practice/contents/testFile.txt"
//    val deleteUrl = "https://api.github.com/repos/robyngar/git_practice/contents/testFile.txt"
//
//    "create a file in a user's repository from the github api and the delete that file" in {
//
//      val getResult = testApplicationConnector.createFile[ReturnCreatedFile](createFile, url)
//
//      Await.result(getResult, Duration("5 second")) shouldBe Right(returnOfCreate)
//
//      val deleteFileResult = testApplicationConnector.deleteFile[DeletedReturn](deleteUrl,)
//
//    }
//  }




}
