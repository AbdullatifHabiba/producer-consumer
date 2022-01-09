export interface Shape{
    id : number;
    shapeType : string;
    color : string;
    position : {    // Position of top left corner , When its a circle >> refers to the center position
        x : number,
        y : number
    }
    isSelected : boolean,

    width ?: number;
    height?: number;

    //Circle only properties
    radius? : number;

    //ellipse only
    radiusX? : number;
    radiusY? : number;

    points? : string;


    //line
    secondPosition?: {
        x: number,
        y: number
    }
}
