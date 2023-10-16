import Header from '../components/Header';
import ContentBox from '../components/WeatherRecommendation/ContentBox';
import PlaylistIcon from '../components/Playlist/PlaylistIcon';
import Wrapper from '../components/Wrapper/Wrapper';

const WeatherRecommend = () => {
  return (
    <>
      <div className="w-screen h-screen bg-gradient-to-b from-[#D5E5F0] to-[#87c4ed]">
        <Header />
        <Wrapper>
          <div className="flex items-center justify-center mt-12">
            <ContentBox />
          </div>
        </Wrapper>
      </div>
      <PlaylistIcon />
    </>
  );
};

export default WeatherRecommend;
