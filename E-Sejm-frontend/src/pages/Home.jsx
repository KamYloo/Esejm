import React from "react";
import EventCalendar from "../components/EventCalendar.jsx"; // Import useNavigate

function Home() {

    return (
        <div className="grid grid-cols-3 gap-6 p-36 h-full">
            {/* Lewa sekcja */}
            <div className="col-span-2 grid grid-rows-2">
                <div className="grid grid-cols-2 gap-6 h-1/3">
                    <div className="bg-gray-400 rounded-md aspect-square">main news</div>

                    <div className="grid grid-rows-2 gap-4 h-full">
                        <div className="bg-gray-400 rounded-md">co-main news1</div>
                        <div className="bg-gray-400 rounded-md">co-main news2</div>
                    </div>
                </div>

                <div className="grid grid-cols-3 gap-4">
                    <div className="bg-gray-400 aspect-square rounded-md">news1</div>
                    <div className="bg-gray-400 aspect-square rounded-md">news2</div>
                    <div className="bg-gray-400 aspect-square rounded-md">news3</div>
                    <div className="bg-gray-400 aspect-square rounded-md">news4</div>
                    <div className="bg-gray-400 aspect-square rounded-md">news5</div>
                    <div className="bg-gray-400 aspect-square rounded-md">news6</div>
                </div>

                <div className="bg-gray-300 grid grid-cols-5 gap-4 p-5 rounded-md mt-6">
                    <div className="col-span-5 text-center font-bold text-lg mb-4">
                        Top 5 najbardziej zaufanych polityków
                    </div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik1</div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik2</div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik3</div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik4</div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik5</div>

                    <div className="col-span-5 text-center font-bold text-lg mt-4">
                        Top 5 najbardziej niezaufanych polityków
                    </div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik6</div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik7</div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik8</div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik9</div>
                    <div className="bg-gray-400 aspect-square rounded-md">wynik10</div>
                </div>
            </div>

            {/* Prawa sekcja */}
            <div className="col-span-1 flex flex-col gap-6 ">
                <div className="bg-gray-400 rounded-md aspect-square mb-8">
                    <EventCalendar />
                </div>

                <div className="bg-gray-400 flex flex-col justify-between p-8 rounded-md h-2/3 mt-20 ">
                    <div className="text-center font-bold">Aktywność na forum</div>
                    <div className="bg-gray-500 flex-1 rounded-md mb-4 flex items-center justify-center">temat1</div>
                    <div className="bg-gray-500 flex-1 rounded-md mb-4 flex items-center justify-center">temat2</div>
                    <div className="bg-gray-500 flex-1 rounded-md mb-4 flex items-center justify-center">temat3</div>
                    <div className="bg-gray-500 flex-1 rounded-md flex items-center justify-center">temat4</div>
                </div>
            </div>
        </div>
    );
}

export default Home;
