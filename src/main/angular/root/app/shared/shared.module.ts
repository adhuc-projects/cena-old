import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { MatFormFieldModule, MatInputModule, MatButtonModule, MatToolbarModule } from "@angular/material";
import { TranslateModule } from "@ngx-translate/core";

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    TranslateModule
  ]
})
export class SharedModule { }
