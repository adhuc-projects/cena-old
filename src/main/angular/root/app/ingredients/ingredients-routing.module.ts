import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

import { SharedModule } from "@shared/shared.module";
import { IngredientsListComponent } from "@ingredients/ingredients-list/ingredients-list.component";
import { IngredientsGuardService } from "@ingredients/ingredients-guard.service";

const routes: Routes = [
    {
      path: "",
      component: IngredientsListComponent,
      canActivate: [IngredientsGuardService]
    }
  ];

@NgModule({
  imports: [
    SharedModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule],
  providers: [
    IngredientsGuardService
  ]
})
export class IngredientsRoutingModule {}
