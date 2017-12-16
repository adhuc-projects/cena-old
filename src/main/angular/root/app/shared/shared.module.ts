import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MatFormFieldModule, MatInputModule, MatButtonModule, MatToolbarModule } from "@angular/material";
import { TranslateModule } from "@ngx-translate/core";

@NgModule({
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    TranslateModule
  ],
  declarations: [],
  exports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    TranslateModule
  ]
})
export class SharedModule { }
