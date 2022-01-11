import { WebSocketAPI } from './../websocket.service';
import { Link } from './../Components/canvas/Link';
import { Injectable } from '@angular/core';
import { MousePosition } from '../MousePosition';
import { Shape } from '../Shape';
import { ShapesService } from './shapes.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class ControllerService {
  constructor(
    private shapeService: ShapesService,
    private httpclient: HttpClient,
    private WebSocket:WebSocketAPI
  ) {

  }

  shapePoints: MousePosition[] = [];
  leftshift = 260;

  currentTool = {
    name: 'select',
  };
  makeline:boolean=false;

  toolClicked(tool: string) {
    this.resetShapePoints();

    if (tool == 'undo') {
     // this.undo();
    } else if (tool == 'redo') {
      //this.redo();
    }
    else {
      this.currentTool.name = tool;
      this.shapeService.unSelectAll();
    }
  }
  lineposition:Array<Shape  >=[];

  shapeClicked(event: MouseEvent, shape: Shape) {
    if (this.currentTool.name == 'select' ) {

      console.log(shape.id);
      console.log("links",this.shapeService.links);

      this.shapeService.unSelectAll();
      this.shapeService.selectShape(shape);
      shape.isSelected = true;
    } else if (this.currentTool.name == 'line') {
      console.log(shape.id);
      console.log("links",this.shapeService.links);
      console.log("shapes",this.shapeService.shapes);

      this.shapeService.unSelectAll();
      this.shapeService.selectShape(shape);
      shape.isSelected = true;


        if (this.lineposition.length==0){
           this.lineposition.push(shape);
           return

        }
        if (this.lineposition.length==1){

          if(this.lineposition[this.lineposition.length-1].shapeType==shape.shapeType){
            window.alert("you can't with same shape")
            console.log("you can't with same shape");this.lineposition=[];return;}
          else{
          this.lineposition.push(shape);


          }
        }
        if(this.lineposition.length==2){
          let l={sh1:this.lineposition[0],sh2:this.lineposition[1]};
          if(this.checklink(l)){            window.alert("you can't with same shape")
          console.log("last link");  this.lineposition=[]

          return;}
           console.log(l);
          this.shapeService.links.push(l)
           this.lineposition=[]
           console.log("li",this.lineposition);




         }

         }else if (this.currentTool.name == 'Fqueue') {

          this.shapeService.unSelectAll();
          this.shapeService.selectShape(shape);
          shape.isSelected = true;
          this.fqueue=shape;
          this.WebSocket._connect();

          console.log(this.fqueue)

         }
         if (this.currentTool.name == 'line'){           this.lineposition.slice(0,this.lineposition.length);}
  }
  private fqueue: Shape | null = null;

checklink(l:Link):boolean{

  let ch:boolean=false;
    this.shapeService.links.forEach((link)=>{if(link==l||(link.sh1==l.sh2&&link.sh2==l.sh1))ch= true;})

return ch;


}

  /*  Custom Shape */

  addShapePoint(event: MouseEvent) {
    let currentPosition: MousePosition = {
      x: event.x - this.leftshift,
      y: event.y,
    };
    this.shapePoints.push(currentPosition);
  }

  resetShapePoints() {
    this.shapePoints = [];
  }




  results: any;
  serverUrlsingle = 'http://localhost:8080/action/';
  tree:any={};

  file:any;
  public formData = new FormData();


  buildtree(){
    this.tree["root"] = this.fqueue;
    this.tree["products"] = this.shapeService.Products;
    this.tree["links"] = this.shapeService.links;
    this.tree["shapes"] = this.shapeService.shapes;
    this.formData.append('Tree',JSON.stringify(this.tree));
    this.WebSocket._send(this.tree);


  }
recievetree(){
   console.log("tree"+this.WebSocket.onMessageReceived);


}

  createShapePrototype(point1: MousePosition, point2: MousePosition) {
    if (
      point1.x == point2.x &&
      point1.y == point1.y &&
      point2.y == point2.y
    ) {
      return;
    }

    switch (this.currentTool.name) {
      case 'rectangle': {
        let shape = {
          shapeType: 'rectangle',
          position: {
            x: point1.x < point2.x ? point1.x : point2.x,
            y: point1.y < point2.y ? point1.y : point2.y,
          },
          width:120 ,//Math.abs(point2.x - point1.x),
          height:60 //Math.abs(point2.y - point1.y),
        };

        this.shapeService.addShapePrototype(shape);
        break;
      }

      case 'circle': {
        let shape = {
          shapeType: 'circle',
          position: {
            x: point1.x,
            y: point1.y,
          },
          radius:50
          /*  Math.abs(point2.x - point1.x) > Math.abs(point2.y - point1.y)
              ? Math.abs(point2.x - point1.x)
              : Math.abs(point2.y - point1.y),*/
        };

        this.shapeService.addShapePrototype(shape);
        break;
      }



    }
  }

}
