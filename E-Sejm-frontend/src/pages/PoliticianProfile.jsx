import React, {useEffect} from "react";
import {useParams} from "react-router-dom";
import {getMpByIdAction} from "../redux/MpService/Action.js";
import {useDispatch, useSelector} from "react-redux";
import { PieChart } from '@mui/x-charts/PieChart';
import {BarChart} from "@mui/x-charts";
import okreg from "../assets/okreg13.png";


export function PoliticianProfile(){
    const {politicianId} = useParams();
    const dispatch = useDispatch();
    const { findMp } = useSelector((store) => store.mp);

    useEffect(() => {
        dispatch(getMpByIdAction(politicianId));
    }, [dispatch]);

    return (
        <div className=" min-h-screen flex flex-col mx-40">
            <div className="flex shadow-md">
                <div className="flex w-3/5">
                    <div className="flex-none content-center m-10">
                        <img
                            src={findMp?.photo}
                            alt={`${findMp?.firstName} ${findMp?.lastName}`}
                            className="w-40 h-40 rounded-full"
                        />
                    </div>
                    <div className="flex-initial content-center mx-20">
                        <h1 className="text-2xl font-semibold">
                            {findMp?.firstName} {findMp?.secondName} {findMp?.lastName}
                        </h1>
                        <p className="text-base font-semibold">
                            <span className="font-bold">Ur.</span> {findMp?.birthDate}
                        </p>
                        <p className="text-base font-semibold">
                            <span className="font-bold">Wyształcenie:</span> {findMp?.educaonLevel}
                        </p>
                        <p className="text-base font-semibold">
                            <span className="font-bold">Zawód:</span> {findMp?.profession}
                        </p>
                        <p className="text-base font-semibold">
                            <span className="font-bold">Partia:</span> {findMp?.club}
                        </p>
                        <p className="text-base font-semibold">
                            <span className="font-bold">Okręg wyborczy:</span> {findMp?.districtName}
                        </p>
                        <p className="text-base font-semibold">
                            <span className="font-bold">Numer okręgu wyborczego:</span> {findMp?.districtNum}
                        </p>

                    </div>
                </div>
                <div className="w-2/5 justify-center">
                    <p className="text-blue-900 text-3xl font-bold mt-10 flex justify-center">
                        Wystąpienia i wnioski
                    </p>
                    <div className="h-2 bg-blue-900 mt-10 mx-20 rounded"></div>
                    <div className="mx-10">
                        <BarChart
                            xAxis={[
                                {
                                    scaleType: 'band',
                                    data: ['2024'],
                                    categoryGapRatio: 0.5
                                },
                            ]}
                            series={[
                                {data: [10]},
                                {data: [20]},
                            ]}
                            width={window.width}
                            height={300}
                        />
                    </div>
                </div>
            </div>
            {/*<div className="w-full h-80 bg-mp_activity_background bg-no-repeat bg-cover mt-20">*/}
                <div className="w-full h-96 bg-gradient-to-r from-[#03143d] to-[#344ab6] bg-no-repeat bg-cover mt-20 rounded">
                <p className="text-4xl text-white flex justify-center pt-10">Aktywność</p>
                <div className="flex justify-around mt-5 mx-20">
                    <div className="flex-col justify-items-center">
                        <PieChart
                            // colors= {['#2066a8', '#8cc5e3']}
                            series={[
                                {
                                    data: [
                                        {id: 0, value: findMp?.yesVotes, label: 'yes'},
                                        {id: 1, value: findMp?.noVotes, label: 'no'},
                                    ],
                                    startAngle: -150,
                                    endAngle: 150,
                                    innerRadius: 45,
                                    outerRadius: 70,
                                    cx: 95
                                },
                            ]}

                            slotProps={{
                                legend: {
                                    hidden: true,
                                },
                            }}
                            width={200}
                            height={200}
                        />
                        <p className="text-white text-xl">
                            Głosy tak, nie
                        </p>
                    </div>
                    <div className="flex-col justify-items-center">
                        <PieChart
                            series={[
                                {
                                    data: [
                                        {id: 0, value: findMp?.yesVotes + findMp?.noVotes},
                                        {id: 1, value: findMp?.abstentionsCount},
                                    ],
                                    startAngle: -150,
                                    endAngle: 150,
                                    innerRadius: 45,
                                    outerRadius: 70,
                                    cx: 95
                                },
                            ]}
                            slotProps={{
                                legend: {
                                    hidden: true,
                                },
                            }}
                            width={200}
                            height={200}
                        />
                        <p className="text-white text-xl">
                            Frekwencja na głosowaniach
                        </p>
                    </div>
                    <div className="flex-col justify-items-center">
                        <PieChart
                            series={[
                                {
                                    data: [
                                        {id: 0, value: findMp?.yesVotes, label: 'yes'},
                                        {id: 1, value: findMp?.noVotes, label: 'no'},
                                    ],
                                    startAngle: -150,
                                    endAngle: 150,
                                    innerRadius: 45,
                                    outerRadius: 70,
                                    cx: 95
                                },
                            ]}

                            slotProps={{
                                legend: {
                                    hidden: true,
                                },
                            }}
                            width={200}
                            height={200}
                        />
                        <p className="text-white text-xl">
                            Zgodność z partią
                        </p>
                    </div>
                </div>
            </div>
            <div className="flex mt-20 shadow-2xl">
                <div className="w-3/5 p-10">
                    <p className="text-4xl font-bold">
                        Wyniki głosowania na {findMp?.firstName} {findMp?.lastName}
                    </p>
                    <p className="text-2xl font-bold pt-10">
                        Liczba głosów na kandydata: {findMp?.numberOfVotes}
                    </p>
                    <p className="text-2xl font-bold pt-10">
                        Procent głosów na kandydata: 5%
                    </p>
                </div>
                <div className="w-2/5">
                    {/*<div className="w-full h-full bg-red-700">*/}

                    {/*</div>*/}
                    <img
                        src={okreg}
                    />
                </div>
            </div>
        </div>
    );
}