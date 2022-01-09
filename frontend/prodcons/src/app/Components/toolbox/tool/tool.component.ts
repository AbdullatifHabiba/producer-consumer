import { Component, Input, OnInit } from '@angular/core';
import { ControllerService } from 'src/app/Services/controller.service';


@Component({
  selector: 'app-tool',
  templateUrl: './tool.component.html',
  styleUrls: ['./tool.component.css']
})
export class ToolComponent implements OnInit {

  @Input() toolName : string = "";
  @Input() iconLibrary : string = "";
  @Input() iconName : string = "";
  @Input() color : string = "";

  currentTool : {"name" : string} = { "name" : ""};
  
  constructor(private controller: ControllerService) { }

  ngOnInit(): void {
    this.currentTool = this.controller.currentTool;
  }

  toolClicked() {
    this.controller.toolClicked(this.toolName);
  }

}
