package controllers

import models.{NewTodoListItem, TodoListItem}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._

import javax.inject.Singleton
import javax.inject.Inject
import scala.collection.mutable

@Singleton
class TodoController @Inject() (val controllerComponents:ControllerComponents) extends BaseController {

  implicit val todoListJson: OFormat[TodoListItem] = Json.format[TodoListItem]
  implicit val newTodoListJson: OFormat[NewTodoListItem] = Json.format[NewTodoListItem]

  private val todoList = new mutable.ListBuffer[TodoListItem]()
  todoList += TodoListItem(1, "test", isItDone = true)
  todoList += TodoListItem(2, "some other value", isItDone = false)

  def getAll: Action[AnyContent] = Action {
    if(todoList.isEmpty) {
      NoContent
    } else {
      Ok(Json.toJson(todoList))
    }
  }

  def getById(itemId:Long): Action[AnyContent] = Action {
    val foundItem = todoList.find(_.id == itemId)
    foundItem match {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound
    }
  }

  def markAsDone(itemId: Long): Action[AnyContent] = Action {
    val foundItem = todoList.find(_.id == itemId)
    foundItem match {
      case Some(item) =>
        val updated = item.copy(isItDone = true)
        todoList.remove(todoList.indexOf {
          foundItem.get
        })
        todoList += updated
        Ok(Json.toJson(updated))
      case None => NotFound
    }
  }

  def deleteAllDone(): Action[AnyContent] = Action {
    val doneItems = todoList.filter(item => item.isItDone)
    doneItems.map(item => todoList.remove(todoList.indexOf {
      item
    }))
    NoContent
  }

  def addNewItem(): Action[AnyContent] = Action { implicit request =>
    val jsonContent = request.body.asJson
    val todoListItem: Option[NewTodoListItem] = jsonContent.flatMap(
      Json.fromJson[NewTodoListItem](_).asOpt
    )
      todoListItem match {
        case Some(newItem) =>
          val newId = todoList.map(_.id).max + 1
          val nextItem = TodoListItem(id = newId,description = newItem.description,isItDone = false)
          todoList += nextItem
          Created(Json.toJson(nextItem))
        case None =>
          BadRequest
      }
    }
}
