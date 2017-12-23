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

  public static readonly RECIPES_LINK_NAME = "recipes";
  public static readonly MENUS_LINK_NAME = "menus";
  public static readonly INGREDIENTS_MANAGEMENT_LINK_NAME = "ingredientsManagement";

  searchMenuHidden = true;
  recipesMenuHidden = true;
  menusMenuHidden = true;
  ingredientsMenuHidden = true;

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.initMenuVisibility();
  }

  initMenuVisibility() {
    this.apiService.getApiIndex().subscribe(resource => {
      this.searchMenuHidden = resource.getLink(HeaderComponent.RECIPES_LINK_NAME) == null;
      this.recipesMenuHidden = resource.getLink(HeaderComponent.RECIPES_LINK_NAME) == null;
      this.menusMenuHidden = resource.getLink(HeaderComponent.MENUS_LINK_NAME) == null;
      this.ingredientsMenuHidden = resource.getLink(HeaderComponent.INGREDIENTS_MANAGEMENT_LINK_NAME) == null;
    });
  }

}
