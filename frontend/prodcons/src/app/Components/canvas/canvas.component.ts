import { Product } from 'src/app/product';
import { Link } from './Link';
import { Component, ElementRef, OnChanges, OnInit } from '@angular/core';
import { Shape } from '../../Shape';
import { ShapesService } from '../../Services/shapes.service';
import { ControllerService } from 'src/app/Services/controller.service';
import { MousePosition } from 'src/app/MousePosition';


@Component({
  selector: 'app-canvas',
  templateUrl: './canvas.component.html',
  styleUrls: ['./canvas.component.css'],
})
export class CanvasComponent implements OnInit {
  shapes: Shape[] = [];
  dots: Product[] = [];
  leftshift = 260;

  constructor(
    private shapesService: ShapesService,
    private controller: ControllerService,
    private ele: ElementRef
  ) {}

  ngOnInit(): void {
    this.shapes = this.shapesService.shapes;

  }

  canvasClick(event: MouseEvent) {
    this.dots = this.shapesService.Products;

    console.log(this.dots);

       this.shapesService.unSelectAll();
  }
  canvasClicked(event: MouseEvent) {
    if (this.controller.currentTool.name == 'select'||this.controller.currentTool.name == 'line') {
      this.controller.resetShapePoints();
    }
  }

  makeline:boolean=false
  Links:Array<Link>=[];

  shapeClicked(event: MouseEvent, shape: Shape) {
    this.controller.shapeClicked(event, shape);
    this.makeline=this.controller.makeline;

    this.Links=this.shapesService.links;


  }
  linkisselected=false;

  linkClicked(event: MouseEvent, link: Link) {
    this.linkisselected=true

  }

  lineposition:Array<Shape  >=[];

lx:number=0;ly:number=0;l2x:number=0;l2y:number=0;


  isSelectionTool(): boolean {
    return this.controller.currentTool.name == 'select';
  }
  black="black"

  startingPosition = {x:0, y:0};
  mouseMoveRef: Function = () => {};
  mouseUpRef: Function = () => {};

  mouseDownHandler(event: MouseEvent) {
    this.startingPosition.x = event.clientX - this.leftshift;
    this.startingPosition.y = event.clientY;

    this.mouseMoveRef = this.mouseMoveHandler.bind(this);
    this.mouseUpRef = this.mouseUpHandler.bind(this);

    this.ele.nativeElement.addEventListener('mousemove', this.mouseMoveRef);
    this.ele.nativeElement.addEventListener('mouseup', this.mouseUpRef);
  }

  mouseMoveHandler(e: MouseEvent) {
    let currentPosition = {
      x: e.clientX - this.leftshift,
      y: e.clientY,
    };
    this.controller.createShapePrototype(
      { x: this.startingPosition.x, y: this.startingPosition.y },
      currentPosition
    );
  }

  mouseUpHandler() {
    this.shapesService.confirmShapePrototype();

    this.ele.nativeElement.removeEventListener('mousemove', this.mouseMoveRef);
    this.ele.nativeElement.removeEventListener('mouseup', this.mouseUpRef);
  }

}
