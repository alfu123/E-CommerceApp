import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable()
export class DataService {
    //set sidenav default state to open
    private showDrawer = new BehaviorSubject(true);
    currentState = this.showDrawer.asObservable();

    constructor() { }

    // Change Sidenav current state
    changeState(open: boolean) {
        this.showDrawer.next(open);
    }
}