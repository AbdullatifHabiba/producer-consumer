import { MousePosition } from 'src/app/MousePosition';
import { Position } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ControllerService } from 'src/app/Services/controller.service';
import { ShapesService } from 'src/app/Services/shapes.service';
import { Shape } from 'src/app/Shape';
import { Product } from 'src/app/product';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  properties : Shape = {
    id: 0,
    shapeType: '',
    color: '',
    position: {
      x: 0,
      y: 0
    },
    isSelected: false,

  };


  selectedTool = {"name":""};
  pathfile='';


  constructor(private controller : ControllerService,
              private shapeService : ShapesService
    ) { }

  ngOnInit(): void {
    this.selectedTool = this.controller.currentTool;
  }
  addsh(){
    this.controller.makeline=true;
  }

  confirmShape() {
    this.controller.makeCustomShape();
  }

  getSelectedShape() : Shape {
    this.properties = this.shapeService.getSelectedShape()!;
    return this.properties;
  }

  isAnyShapeSelected() : boolean {
    if(this.shapeService.getSelectedShape() == null) {
      return false;
    }
    return true
  }

  deleteShape() {
    this.shapeService.deleteSelectedShape();
  }

  cloneShape() {
    console.log(this.shapeService.getSelectedShape()!.id);
    // TODO Make http request
  }

  Startsimulate() {
    //this.controller.load(this.pathfile);
  }

  stop() {
    //this.controller.save(this.pathfile);
  }
  replay(){}
  num:number=0;

  getnum(){
    this.shapeService.Products=this.makeprods(this.num);

  }

  makeprods(n:number){
   let prs:Array<Product>=[];
    for(let i=0;i<n;i++){
      prs.push({id:i,color:('#'+Math.floor(Math.random()*16777215))})
    }
  console.log(prs)
  return prs;
  }
}
