import { NgModule } from "@angular/core";
import { RouterModule, Route } from "@angular/router";
import { AppComponent } from "./app.component";
import { AuthGuard } from "./auth.guard";
import { LoginComponent } from "./login/login.component";
import { ProfileComponent } from "./profile/profile.component";
import { SignUpComponent } from "./sign-up/sign-up.component";

const routes: Route[] = [
    {
        path: 'home',
        component: ProfileComponent,
        canActivate: [AuthGuard]
    },
    {
        path: 'sign-up',
        component: SignUpComponent
    },
    {
        path: '',
        component: LoginComponent
    },
    {
        path: "**",
        component: AppComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}