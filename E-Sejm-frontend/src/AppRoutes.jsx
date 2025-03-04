import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Home from "./pages/Home.jsx";
import { News } from "./pages/News.jsx";
import { Login } from "./pages/Login.jsx"
import { Register } from "./pages/Register.jsx"
import UserProfile from './pages/UserProfile';
import UserSettings from './pages/UserSettings';
import {Toaster} from "react-hot-toast";
import ContinueLoginWithGoogle from "./components/ContinueLoginWithGoogle.jsx";
import {AdminPanel} from "./admin/AdminPanel.jsx";
import NewsDetails from "./pages/NewsDetails";
import RedactorNewsPanel from "./pages/RedactorNewsPanel.jsx";
import PartyDetailsv2 from "./pages/PartyDetailsv2.jsx";
import { ForumPage } from './pages/ForumPage.jsx'
import {Politicians} from "./pages/Politicians.jsx";
import {PoliticianProfile} from "./pages/PoliticianProfile.jsx";
import VotingPage from "./pages/VotingPage.jsx";
import VotingDetailsPage from "./pages/VotingDetailsPage.jsx";


import {Layout} from "./Layout/Layout.jsx";
import {NoLayout} from "./Layout/NoLayout.jsx";
import ScrollToTop from "./Layout/ScrollToTop.js";
import {ForgotPassword} from "./pages/ForgotPassword.jsx";
import {ChangePassword} from "./pages/ChangePassword.jsx";
import {CheckMail} from "./pages/CheckMail.jsx";
import AddThread from './pages/AddThread.jsx'
import { ThreadPage } from './pages/ThreadPage.jsx'
import Proceeding from "./pages/Proceeding.jsx";
import StatementDetails from "./pages/StatementDetails.jsx";


function AppRoutes() {
    return (

        <Router>
            <ScrollToTop />
            <Toaster
                position="bottom-right"
                reverseOrder={false}
            />
            <Routes>

                <Route path="/" element={<Navigate to="/stronaGlowna" />} />

                <Route element={<NoLayout/>}>
                    <Route path="/logowanie" element={<Login/>} />
                    <Route path="/rejestracja" element={<Register/>} />
                    <Route path="/admin/*" element={<AdminPanel />}/>
                    <Route path="/forgot-password" element={<ForgotPassword/>}/>
                    <Route path="/change-password/:userID/:token" element={<ChangePassword/>}/>
                    <Route path="/check-mail" element={<CheckMail/>}/>
                </Route>

                <Route element={<Layout/>}>
                    <Route path="/stronaGlowna" element={<Home/>} />
                    <Route path="/wiadomosci" element={<News/>} />
                    <Route path="/wiadomosci/:newsId" element={<NewsDetails/>} />
                    <Route path="/user-profile/:nickName" element={<UserProfile/>} />
                    <Route path="/user-settings" element={<UserSettings/>} />
                    <Route path="/poslowie" element={<Politicians/>} />
                    <Route path="/politician-profile/:politicianId" element={<PoliticianProfile/>} />
                    <Route path="/admin-news-panel" element={<RedactorNewsPanel/>} />
                    <Route path="/forum" element={<ForumPage/>} />
                    <Route path="/dodaj-temat" element={<AddThread/>} />
                    <Route path="/forum/:threadId" element={<ThreadPage/>}/>
                    <Route path="/glosowania" element={<VotingPage/>} />
                    <Route path="/glosowania/:votingId" element={<VotingDetailsPage/>} />
                    <Route path="/posiedzenie/:proceedingId/date/:date" element={<Proceeding/>} />
                    <Route path="/posiedzenie/:proceedingId/date/:date/wypowiedz/:statementId/num/:statementNum" element={<StatementDetails/>} />
                    <Route path="/partie" element={<PartyDetailsv2/>} />
                </Route>
            </Routes>

            <ContinueLoginWithGoogle />
        </Router>
    );
}

export default AppRoutes;

