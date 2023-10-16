import playlistdisc from '../assets/images/playlistdisc.png';
import playlistimg from '../assets/images/Rectangle 64.png';
import 'animate.css';

export type InsidePly = {
  title: string;
  memberId: number;
  playlistId: number;
};

const MyPlayList = ({ myPlayList }: { myPlayList: InsidePly[] }) => {
  return (
    <>
      <div className="flex flex-col items-center w-full">
        <div className="flex justify-center ">
          <img
            className="w-[250px] h-[250px] animate-spin-slow"
            src={playlistdisc}
            alt="플레이리스트디스크"
          />
        </div>
        <div className="flex flex-col w-[875px] items-center justify-center ">
          <div className="flex w-[875px] h-[67px] items-center border-b-[1px] border-solid border-[#000000] justify-center">
            <span className="w-[50px] text-center text-xs">No.</span>
            <span className="w-[100px] text-center text-xs">Album cover</span>
            <span className="w-[100px] text-center text-xs">Title</span>
            <span className="w-[500px] text-center text-xs">Content</span>
            <button className="w-[50px] text-center text-xs">좋아요순</button>
            <button className="w-[50px] text-center text-xs">최신순</button>
          </div>
          <div>
            <table className="flex flex-col w-[875px] items-center justify-center">
              {myPlayList.map((el, idx) => (
                <tr className="flex w-[875px] h-[67px] items-center border-b-[1px] border-solid border-[#bdc2f8] justify-center">
                  <td className="w-[50px] text-center text-xs">{idx + 1}</td>
                  <td className="w-[100px]">
                    <img src={playlistimg} alt="임시사진" />
                  </td>
                  <td className="w-[100px] text-center text-xs">{el.title}</td>
                  <td className="w-[500px] text-center text-xs">컨텐츠내용</td>
                  <td className="w-[50px] text-center text-xs">89</td>
                  <td className="w-[50px] text-center text-xs">190</td>
                </tr>
              ))}
            </table>
          </div>
        </div>
      </div>
    </>
  );
};

export default MyPlayList;
