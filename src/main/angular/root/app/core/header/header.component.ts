import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { Observable } from "rxjs/Observable";

import { ApiService } from "@app/shared/api.service";

@Component({
  selector: "cena-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.css"]
})
export class HeaderComponent implements OnInit {

  searchMenuHidden = true;
  recipesMenuHidden = true;
  menusMenuHidden = true;
  ingredientsMenuHidden = true;

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.hasApiLink("recipes").subscribe(exists => this.searchMenuHidden = !exists);
    this.hasApiLink("recipes").subscribe(exists => this.recipesMenuHidden = !exists);
    this.hasApiLink("menus").subscribe(exists => this.menusMenuHidden = !exists);
    this.hasApiLink("ingredientsManagement").subscribe(exists => this.ingredientsMenuHidden = !exists);
  }

  hasApiLink(rel: string): Observable<boolean> {
    return this.apiService.getLink(rel).map(link => link != null);
  }

}
