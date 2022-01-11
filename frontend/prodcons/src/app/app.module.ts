import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { DragDropModule } from '@angular/cdk/drag-drop'
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";

import { AppComponent } from './app.component';
import { ToolboxComponent } from './Components/toolbox/toolbox.component';
import { CanvasComponent } from './Components/canvas/canvas.component';
import { ToolComponent } from './Components/toolbox/tool/tool.component';
import { ColorPickerModule } from 'ngx-color-picker';
import { SidebarComponent } from './Components/sidebar/sidebar.component';
import { ShapesService } from './Services/shapes.service';
import { ControllerService } from './Services/controller.service';
import { WebSocketAPI } from './websocket.service';


@NgModule({
  declarations: [
    AppComponent,
    ToolboxComponent,
    CanvasComponent,
    ToolComponent,
    SidebarComponent
  ],
  imports: [
    BrowserModule,
    ColorPickerModule,
    DragDropModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [ShapesService, ControllerService,WebSocketAPI],
  bootstrap: [AppComponent]
})
export class AppModule { }
