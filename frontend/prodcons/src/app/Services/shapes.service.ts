import { Link } from './../Components/canvas/Link';
import { Shape } from '../Shape';
import { Product } from '../product';
import { ThisReceiver } from '@angular/compiler';

export class ShapesService {
  shapes: Shape[] = [];

  currentColor: string = 'white';
  private currentID: number = 0;
  private selectedShape: Shape | null = null;
  private prototypeShape : Shape | null = null;

   Products:Array<Product>=[];


  getSelectedShape() {
    return this.selectedShape;
  }

  selectShape(shape : Shape) {
    this.selectedShape = shape;
  }

  addShape(shape: any) {
    shape.id = this.currentID;
    shape.color = this.currentColor;

    this.shapes.push(shape);
    this.currentID += 1;
  }

  deleteSelectedShape() {
    if(this.selectedShape == null){
      console.log("error");
      return;
    }

    const index = this.shapes.indexOf(this.selectedShape);
    const ind=this.selectedShape.id;
    var li;
    for(let lin of this.links)
    {
     if(lin.sh1.id==ind || lin.sh2.id==ind)
     {
       console.log("deleted");

       li=  this.links.indexOf(lin);

       this.links.splice(li,1)
     }
     console.log("links",this.links)



    }

    this.shapes.splice(index, 1);

    this.shapes.forEach((sha)=>{sha.id=this.shapes.indexOf(sha);})
    this.currentID-=1;
    console.log("shapes",this.shapes)

    this.unSelectAll();

  }
  links:Array<Link>=[];



  unSelectAll() {
    if(this.selectedShape != null) {
      this.selectedShape.isSelected = false;
      this.selectedShape = null;
    }
  }

  updateColor(color: string) {
    this.currentColor = color;

    if(this.selectedShape != null) {  // if a shape is selected, change it's color
      this.selectedShape.color = color;
    }
  }

  addShapePrototype(shape: any) {
    if(this.prototypeShape != null) {
      this.shapes.pop();
    }

    shape.id = this.currentID;
    shape.color = this.currentColor;

    this.prototypeShape = shape;
    this.shapes.push(shape);

  }

  confirmShapePrototype() {
    if(this.prototypeShape != null) {
      this.prototypeShape = null;
      this.currentID += 1;
    }
  }

}
