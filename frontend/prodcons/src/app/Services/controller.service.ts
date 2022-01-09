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
    private httpclient: HttpClient
  ) {}

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
        if (this.lineposition.length>0){

          if(this.lineposition[this.lineposition.length-1].shapeType==shape.shapeType){ console.log("you can't with same shape");return;}
          else{
          this.lineposition.push(shape);


          }
        }
        if(this.lineposition.length>=2){
          let l={sh1:this.lineposition[this.lineposition.length-2],sh2:this.lineposition[this.lineposition.length-1]};
          if(this.checklink(l)){ console.log("last link");   this.lineposition.slice(0,this.lineposition.length);
          return;}
           console.log(l);
          this.shapeService.links.push(l)
           this.lineposition.slice(0,this.lineposition.length);



         }

         }else if (this.currentTool.name == 'ellipse') {

          this.shapeService.unSelectAll();
          this.shapeService.selectShape(shape);
          shape.isSelected = true;
          this.fqueue=shape;
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

  makeCustomShape() {
    let points = '';
    for (let i = 0; i < this.shapePoints.length; i++) {
      let currentPoint: string =
        this.shapePoints[i].x.toString() +
        ',' +
        this.shapePoints[i].y.toString() +
        ' ';
      points = points.concat(currentPoint);
    }

    const shape = {
      shapeType: 'customShape',
      points: points,
      isSelected: false,
    };

    this.shapeService.addShape(shape);
    this.resetShapePoints();
  }



  results: any;
  serverUrlsingle = 'http://localhost:8080/shape/';
  undo() {
    this.httpclient
      .get(`${this.serverUrlsingle}/${'undo'}`, {
        observe: 'response',
      })
      .subscribe(
        (response) => {
          this.results = response.body;
          this.shapeService = JSON.parse(this.results);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
  }

  redo() {

  }
  load(serverUrlsingle: string) {
    this.httpclient
      .get('${this.serverUrlsingle}/${load}', {
        params: { path: serverUrlsingle },
        observe: 'response',
      })
      .subscribe(
        (response) => {
          this.results = response.body;
          this.shapeService = JSON.parse(this.results);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
  }

  save(serverUrlsingle: string) {
    this.httpclient
      .get('${this.serverUrlsingle}/${save}', {
        params: { path: serverUrlsingle },
        observe: 'response',
      })
      .subscribe(
        (response) => {
          this.results = response.body;
          this.shapeService = JSON.parse(this.results);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
  }

  cloneShape(id: number) {
    this.httpclient
      .get('${this.serverUrlsingle}/${copy}', {
        params: { id: id },
        observe: 'response',
      })
      .subscribe(
        (response) => {
          this.results = response.body;
          this.shapeService = JSON.parse(this.results);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
  }

  createShapePrototype(point1: MousePosition, point2: MousePosition) {
    if (
      point1.x == point2.x &&
      point2.x == point2.x &&
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

      case 'ellipse': {
        let shape = {
          shapeType: 'ellipse',
          position: {
            x: (point1.x + point2.x) / 2,
            y: (point1.y + point2.y) / 2,
          },
          radiusX: Math.abs(point1.x - point2.x) / 2,
          radiusY: Math.abs(point1.y - point2.y) / 2,
        };

        this.shapeService.addShapePrototype(shape);
        break;
      }

      case 'line': {
        let shape = {
          shapeType: 'line',
          position: point1,
          secondPosition: point2,
        };

        this.shapeService.addShapePrototype(shape);
        break;
      }
    }
  }

}
