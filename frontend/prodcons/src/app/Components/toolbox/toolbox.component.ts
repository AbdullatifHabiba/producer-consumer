import { Component, OnInit } from '@angular/core';
import { ShapesService } from 'src/app/Services/shapes.service';
import { tools } from './tools';

@Component({
  selector: 'app-toolbox',
  templateUrl: './toolbox.component.html',
  styleUrls: ['./toolbox.component.css'],
})
export class ToolboxComponent implements OnInit {
  tools = tools;
  activeTool: string = '';
  color = 'black';

  constructor(private shapesService: ShapesService) {}

  ngOnInit(): void {}

  updateColor() {
    this.shapesService.updateColor(this.color);
  }
}
