package cl.kath.rubypurrs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.kath.rubypurrs.ui.ACCESS_HUB_ROUTE
import cl.kath.rubypurrs.ui.accessGraph
import cl.kath.rubypurrs.ui.screens.*

@Composable
fun NavGraph() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "login") {
        composable("login") { LoginScreen(
            onLogin = { nav.navigate("home") },
            onRegister = { nav.navigate("register") },
            onForgot = { nav.navigate("forgot") }
        ) }
        composable("register") { RegisterScreen(onDone = { nav.popBackStack() }) }
        composable("forgot") { ForgotScreen(onDone = { nav.popBackStack() }) }
        composable("home") {
            HomeScreen(
                goFollow = { nav.navigate("follow") },
                goMouse  = { nav.navigate("mouse") },
                goPet    = { nav.navigate("pet") },
                goAccess = { nav.navigate(ACCESS_HUB_ROUTE) }
            )
        }
        composable("follow") { FollowRubyScreen(onBack = { nav.popBackStack() }) }
        composable("mouse")  { CatchMouseScreen(onBack = { nav.popBackStack() }) }
        composable("pet")    { PettingScreen(onBack = { nav.popBackStack() }) }

        accessGraph(nav)
    }
}
