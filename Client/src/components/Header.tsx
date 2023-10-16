import { Link, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { toast } from 'react-toastify';
import { logout } from '../redux/slice/LoginSlice';
import { RootState } from '../redux/store';
import Logo from '../assets/images/logo.png';
import userImg from '../assets/images/user.png';

const Header = () => {
  const dispatch = useDispatch();
  const history = useNavigate();
  const loginState = useSelector((state: RootState) => state.login.loginState);
  const nickname = useSelector((state: RootState) => state.login.nickname);
  const currentURL = new URL(window.location.href);
  const pathName = currentURL.pathname;

  const buttonClasses = (pathIndex: string) => {
    return `hover:text-sky-400 ${
      pathName === pathIndex ? 'transform scale-150 mx-8' : 'text-[black]'
    }`;
  };

  const signupHandler = () => {
    history('/signup');
  };

  const loginHandler = () => {
    history('/login');
  };

  const logoutHandler = () => {
    dispatch(logout());
    toast.success('로그아웃 되었습니다.');
  };

  return (
    <header className="w-full h-[75px]  flex justify-between items-center text-center font-['Anton-Regular'] border-b-2 border-gray-500 shadow-md ">
      <div className="w-[750px] flex  items-center justify-around text-xl ">
        <Link to="/">
          <img src={Logo} alt="Header Image" />
        </Link>
        <Link to="/community">
          <h2 className={buttonClasses('/community')}>COMMUNITY</h2>
        </Link>
        <Link to="/weatherRecommend">
          <h2 className={buttonClasses('/weatherRecommend')}>RECOMMENDATION</h2>
        </Link>
        <Link to="/mubti">
          <h2 className={buttonClasses('/mubti')}>MUBTI</h2>
        </Link>
        <Link to="/search">
          <h2 className={buttonClasses('/search')}>Search Songs</h2>
        </Link>
      </div>
      <div className="w-[300px] flex justify-around items-center text-lg ">
        {loginState ? (
          <>
            <Link to="/mypage">
              <div className="flex items-center">
                <img
                  src={userImg}
                  alt="유저이미지"
                  className="mr-4 w-[32px] h-[32px] hover:text-sky-400"
                />

                <span className=" hover:text-sky-400">{nickname}</span>
              </div>
            </Link>
            <button onClick={logoutHandler} className=" rounded-xl w-32 h-10 hover:text-sky-400">
              Logout
            </button>
          </>
        ) : (
          <>
            <button
              className=" ml-12 rounded-xl w-24 h-10 hover:bg-opacity-50 hover:text-sky-400"
              onClick={loginHandler}
            >
              log in
            </button>
            <button
              className="   w-24 h-10  rounded-xl hover:opacity-50 hover:text-sky-400"
              onClick={signupHandler}
            >
              sign up
            </button>
          </>
        )}
      </div>
    </header>
  );
};

export default Header;
