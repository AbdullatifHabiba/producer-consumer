/*import { Injectable } from '@angular/core';
import { ControllerService } from 'src/app/Services/controller.service';
import * as Stomp from "stompjs"
import * as SockJS from 'sockjs-client';
@Injectable({
  providedIn: 'root'
})
export class StompService {

constructor(private controller:ControllerService) { }
private connecting: boolean = false;
private topicQueue: any[] = [];

socket = new SockJS('http://localhost:8080/ws');
stompClient = Stomp.over(this.socket);
topic: string = "/topic/posttree";

subscribe(topic: any, callback: any): void {
    // If stomp client is currently connecting add the topic to the queue
    if (this.connecting) {
        this.topicQueue.push({
            topic,
            callback
        });
        return;
    }

    const connected: boolean = this.stompClient.connected;
    if (connected) {
        // Once we are connected set connecting flag to false
        this.connecting = false;
        this.subscribeToTopic(topic, callback);
        return;
    }

    // If stomp client is not connected connect and subscribe to topic
    this.connecting = true;
    this.stompClient.connect({}, (): any => {
        this.subscribeToTopic(topic, callback);

        // Once we are connected loop the queue and subscribe to remaining topics from it
        this.topicQueue.forEach((item:any) => {
            this.subscribeToTopic(item.topic, item.callback);
        })

        // Once done empty the queue
        this.topicQueue = [];
    });
}

private subscribeToTopic(topic: any, callback: any): void {
    this.stompClient.subscribe(topic, (response?:any): any => {
    //  this.onMessageReceived(response);
        callback(response);
    });
}/*
connect() {
  console.log("Initialize WebSocket Connection");

  this.stompClient.connect({},  (frame:any)=> {
      this.stompClient.subscribe(this.topic,  (sdkEvent: any)=> {
          this.onMessageReceived(sdkEvent);
      });
      //_this.stompClient.reconnect_delay = 2000;
  }, this.errorCallBack);
};

disconnect() {
  if (this.stompClient !== null) {
      this.stompClient.disconnect();
  }
  console.log("Disconnected");
}

// on error, schedule a reconnection attempt
errorCallBack(error:any) {
  console.log("errorCallBack -> " + error)
  setTimeout(() => {
      this.connect();
  }, 5000);
}


send(message: any) {
  console.log("calling logout api via web socket");
  this.stompClient.send("/app/action", {}, message);
}

onMessageReceived(message: any) {
  console.log("Message Recieved from Server :: " + message);
  this.controller.recievetree(JSON.stringify(message.body));
}
}*/

