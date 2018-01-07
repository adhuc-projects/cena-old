import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { LicenseComponent } from "@core/license/license.component";

const routes: Routes = [
  { path: "", redirectTo: "search", pathMatch: "full"},
  { path: "search", loadChildren: "app/search/search.module#SearchModule" },
  { path: "ingredients", loadChildren: "app/ingredients/ingredients.module#IngredientsModule" },
  { path: "license", component: LicenseComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
