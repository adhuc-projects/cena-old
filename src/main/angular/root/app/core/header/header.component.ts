import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { Observable } from "rxjs/Observable";

import { ApiService } from "@shared/api.service";

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
    this.updateMenuVisibility();
  }

  updateMenuVisibility() {
    this.apiService.getApiIndex().subscribe(resource => {
      this.searchMenuHidden = resource.getLink(ApiService.RECIPES_LINK_NAME) == null;
      this.recipesMenuHidden = resource.getLink(ApiService.RECIPES_LINK_NAME) == null;
      this.menusMenuHidden = resource.getLink(ApiService.MENUS_LINK_NAME) == null;
      this.ingredientsMenuHidden = resource.getLink(ApiService.INGREDIENTS_MANAGEMENT_LINK_NAME) == null;
    });
  }

}
