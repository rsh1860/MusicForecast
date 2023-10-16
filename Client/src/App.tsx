import React from 'react';
import './App.css';
import { useSelector } from 'react-redux/es/hooks/useSelector';
import { useLocation, Route, Routes } from 'react-router-dom';
import { RouteConst } from './interface/RouteConst';
import PrivateRoute from './interface/PrivateRoute';
import { RootState } from './redux/store';

import 'react-toastify/dist/ReactToastify.css';

import MUBTIMain from './pages/MUBTI/MUBTIMain';
import Survey from './pages/MUBTI/Survey/Survey';
import SurveyMood from './pages/MUBTI/Survey/SurveyMood';
import SurveySituation from './pages/MUBTI/Survey/SurveySituation';
import SurveyLiking from './pages/MUBTI/Survey/SurveyLiking';
import Write from './pages/Write';
import Mypage from './pages/Mypage';
import Community from './pages/Community';
import CommunityDetail from './pages/CommunityDetail';
import WeatherRecommend from './pages/WeatherRecommend';
import SurveyResult from './pages/MUBTI/Survey/SurveyResult';
import SurveyLoading from './pages/MUBTI/Survey/SurveyLoading';
import SearchSongs from './pages/SearchSongs';
import SurveyAdmin from './pages/MUBTI/Survey/SurveyAdmin';

import SignUp from './components/Login/SignUp';
import LoginOn from './components/Login/LoginOn';
import Main from './components/Main/Main';
import EditProfile from './pages/EditProfile';
import IdFind from './components/Login/IdFind';
import PwdFind from './components/Login/PwdFind';
import Motherplayer from './components/Main/Motherplayer';
import CommunityEdit from './pages/CommunityEdit';
import SurveyYoutube from './pages/MUBTI/Survey/SurveyYoutube';
import PwdChange from './components/Login/PwdChange';
import OtherMyPage from './pages/OtherMyPage';

const App = () => {
  const token = useSelector((state: RootState) => state.login.accessToken);
  const showHeaderFooterPaths = [
    '/mubti',
    '/mubti/loading',
    '/mubti/result',
    '/mubti/mood',
    '/mubti/situation',
    '/mubti/liking',
    '/mubti/youtube',
    '/mubti/1',
  ];
  const location = useLocation();

  const showHeaderFooter = (): boolean => {
    return showHeaderFooterPaths.includes(location.pathname);
  };
  return (
    <>
      <Routes>
        <Route
          path={RouteConst.WeatherRecommend}
          element={<PrivateRoute component={WeatherRecommend} authenticated={token} />}
        />

        <Route
          path={RouteConst.MyPage}
          element={<PrivateRoute component={Mypage} authenticated={token} />}
        />
        <Route
          path={RouteConst.Community}
          element={<PrivateRoute component={Community} authenticated={token} />}
        />
        <Route
          path={RouteConst.Write}
          element={<PrivateRoute component={Write} authenticated={token} />}
        />
        <Route
          path={RouteConst.Detail}
          element={<PrivateRoute component={CommunityDetail} authenticated={token} />}
        />

        <Route
          path={RouteConst.MUBTI}
          element={<PrivateRoute component={MUBTIMain} authenticated={token} />}
        />
        <Route
          path={RouteConst.Survey}
          element={<PrivateRoute component={Survey} authenticated={token} />}
        />
        <Route
          path={RouteConst.Mood}
          element={<PrivateRoute component={SurveyMood} authenticated={token} />}
        />
        <Route
          path={RouteConst.Situation}
          element={<PrivateRoute component={SurveySituation} authenticated={token} />}
        />
        <Route
          path={RouteConst.Liking}
          element={<PrivateRoute component={SurveyLiking} authenticated={token} />}
        />
        <Route
          path={RouteConst.Loading}
          element={<PrivateRoute component={SurveyLoading} authenticated={token} />}
        />
        <Route
          path={RouteConst.Result}
          element={<PrivateRoute component={SurveyResult} authenticated={token} />}
        />
        <Route
          path={RouteConst.EditProfile}
          element={<PrivateRoute component={EditProfile} authenticated={token} />}
        />
        <Route
          path={RouteConst.Search}
          element={<PrivateRoute component={SearchSongs} authenticated={token} />}
        />
        <Route path={RouteConst.Main} element={<Main />} />
        <Route path={RouteConst.LoginOn} element={<LoginOn />} />
        <Route path={RouteConst.SignUp} element={<SignUp />} />
        <Route path={RouteConst.IdFind} element={<IdFind />} />
        <Route path={RouteConst.PwdFind} element={<PwdFind />} />
        <Route path={RouteConst.Edit} element={<CommunityEdit />} />
        <Route path={RouteConst.Youtube} element={<SurveyYoutube />} />
        <Route path={RouteConst.AdminRecommend} element={<SurveyAdmin />} />
        <Route path={RouteConst.PwdChange} element={<PwdChange />} />
        <Route path={RouteConst.IdFind} element={<IdFind />} />
        <Route path={RouteConst.PwdFind} element={<PwdFind />} />
        <Route path={RouteConst.Othermypage} element={<OtherMyPage />} />
      </Routes>
      {showHeaderFooter() ? null : (
        <div className="w-full fixed bottom-0 flex justify-center">
          <Motherplayer />
        </div>
      )}
    </>
  );
};

export default App;
