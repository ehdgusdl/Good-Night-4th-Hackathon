import React, { useState, useMemo } from 'react';
import { Wrapper, Card, PrimaryButton, SecondaryButton, SeatBlock, Heading, SubText, CenterColumn, SeatGrid, StageBar, LegendContainer, LegendItem, ColorBox, SummaryCard, SeatListBox, FormTitle, FormContainer, SectionTitle, SelectedSeatText, InputBox, Label, Input, ButtonRow, colors } from './styles.js';
import styled from 'styled-components';
import { ResultContainer, ResultTitle, ResultText } from './resultStyles.js';

// --- 상수 정의 ---
const SEAT_STATUS = {
  AVAILABLE: 'available',
  UNAVAILABLE: 'unavailable',
  SELECTED: 'selected',
};

const PAGE_STATUS = {
  SELECTION: 'selection',
  FORM: 'form',
  RESULT: 'result',
};

// --- 컴포넌트 ---

/**
 * 메인 페이지 (랜딩 페이지) 컴포넌트
 */
const MainPage = ({ onStartBooking }) => {
  return (
    <CenterColumn>
      <Heading>Techeer's 4th Hackathon</Heading>
      <SubText>Good-Night</SubText>
      <SubText>2025년 8월 16일 (토) 오후 3시</SubText>
      <SubText>SV Hall</SubText>
      <SubText style={{color:'#E5E7EB', fontSize:'1.125rem'}}>
        코딩과 열정으로 가득한 Techeer의 네 번째 해커톤,
        <br />
        잊지 못할 성장의 순간을 함께 만들어 보세요.
      </SubText>
      <PrimaryButton onClick={onStartBooking}>참가 신청하기</PrimaryButton>
    </CenterColumn>
  );
};


/**
 * 개별 좌석 컴포넌트
 */
const Seat = ({ label, status, onClick }) => (
  <SeatBlock status={status} onClick={onClick} title={label}>
    {label}
  </SeatBlock>
);

/**
 * 좌석 선택 페이지 컴포넌트
 */
const SeatSelection = ({ seats, onSeatClick, onNext }) => {
  const selectedSeatCount = seats.filter(s => s.status === SEAT_STATUS.SELECTED).length;

  return (
    <>
      <Heading style={{ marginBottom: '1.5rem' }}>참가 좌석 선택</Heading>
      <StageBar>STAGE</StageBar>
      <SeatGrid>
        {seats.map((seat) => (
          <Seat key={seat.id} label={seat.label} status={seat.status} onClick={() => onSeatClick(seat.id)} />
        ))}
      </SeatGrid>
      <LegendContainer>
        <LegendItem><ColorBox color={colors.primary} />선택 가능</LegendItem>
        <LegendItem><ColorBox color={colors.green500} />선택됨</LegendItem>
        <LegendItem><ColorBox color={colors.gray400} />불가</LegendItem>
      </LegendContainer>
      <SummaryCard>
        <h2 style={{fontSize:'1.125rem', fontWeight:'600', marginBottom:'1rem'}}>선택한 좌석</h2>
        <SeatListBox>
          <p className="text-base sm:text-lg font-bold text-gray-200">
            {selectedSeatCount > 0 ? seats.filter(s => s.status === SEAT_STATUS.SELECTED).map(s => s.label).join(', ') : '좌석을 선택하세요.'}
          </p>
        </SeatListBox>
        <PrimaryButton onClick={onNext} disabled={selectedSeatCount === 0}>
          {selectedSeatCount}개 좌석 선택 완료
        </PrimaryButton>
      </SummaryCard>
    </>
  );
};

/**
 * 예약자 정보 입력 폼 컴포넌트
 */
const ReservationForm = ({ selectedSeats, onConfirm, onBack }) => {
  const [fname, setFname] = useState('');
  const [lname, setLname] = useState('');
  const [email, setEmail] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (fname && lname && email) {
      onConfirm({ fname, lname, email });
    } else {
      console.log("모든 정보를 입력해주세요.");
    }
  };

  return (
    <>
      <FormTitle>참가자 정보 입력</FormTitle>
      <FormContainer>
        <SectionTitle>선택 좌석</SectionTitle>
        <SelectedSeatText>{selectedSeats.map(s => s.label).join(', ')}</SelectedSeatText>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <Label htmlFor="lname">성 (Last Name)</Label>
            <Input id="lname" type="text" value={lname} onChange={(e) => setLname(e.target.value)} />
          </div>
          <div className="mb-4">
            <Label htmlFor="fname">이름 (First Name)</Label>
            <Input id="fname" type="text" value={fname} onChange={(e) => setFname(e.target.value)} />
          </div>
          <div className="mb-6">
            <Label htmlFor="email">이메일</Label>
            <Input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
          </div>
                    <ButtonRow>
            <SecondaryButton type="button" onClick={onBack}>뒤로가기</SecondaryButton>
            <PrimaryButton type="submit" disabled={!fname || !lname || !email}>신청 완료</PrimaryButton>
          </ButtonRow>
        </form>
      </FormContainer>
    </>
  );
};

/**
 * 예약 결과 페이지 컴포넌트
 */
const ResultPage = ({ isSuccess, onReset }) => (
  <ResultContainer>
    <ResultTitle isSuccess={isSuccess}>{isSuccess ? '신청 완료!' : '신청 실패'}</ResultTitle>
    <ResultText>
      {isSuccess
        ? '해커톤 참가가 성공적으로 신청되었습니다.'
        : '죄송합니다. 서버 오류로 신청에 실패했습니다. 잠시 후 다시 시도해주세요.'}
    </ResultText>
    <PrimaryButton onClick={onReset}>처음으로 돌아가기</PrimaryButton>
  </ResultContainer>
);

/**
 * 예매 절차 전체를 관리하는 컴포넌트
 */
const BookingProcess = () => {
    const [seats, setSeats] = useState([]);
    const [currentPage, setCurrentPage] = useState(PAGE_STATUS.SELECTION);
    const [reservationResult, setReservationResult] = useState(null);

    // 컴포넌트 마운트 시 좌석 초기화
    useState(() => {
        const generateInitialSeats = () => {
            const initialSeats = [];
            for (let i = 1; i <= 9; i++) {
                const isUnavailable = Math.random() < 0.2;
                initialSeats.push({
                    id: i,
                    label: `A${i}`,
                    status: isUnavailable ? SEAT_STATUS.UNAVAILABLE : SEAT_STATUS.AVAILABLE,
                });
            }
            return initialSeats;
        };
        setSeats(generateInitialSeats());
    }, []);

    const selectedSeats = useMemo(() => seats.filter(s => s.status === SEAT_STATUS.SELECTED), [seats]);

    const handleSeatClick = (seatId) => {
        setSeats(seats.map(seat => {
            if (seat.id === seatId && seat.status !== SEAT_STATUS.UNAVAILABLE) {
                const newStatus = seat.status === SEAT_STATUS.SELECTED ? SEAT_STATUS.AVAILABLE : SEAT_STATUS.SELECTED;
                return { ...seat, status: newStatus };
            }
            return seat;
        }));
    };

    const handleConfirmReservation = (userInfo) => {
        console.log("참가 신청 시도:", userInfo, selectedSeats.map(s => s.label));
        const isSuccess = Math.random() < 0.99;
        setReservationResult(isSuccess);
        setCurrentPage(PAGE_STATUS.RESULT);
    };
    
    const resetProcess = () => {
        setSeats(seats.map(seat => ({
            ...seat,
            status: seat.status === SEAT_STATUS.UNAVAILABLE ? SEAT_STATUS.UNAVAILABLE : SEAT_STATUS.AVAILABLE
        })));
        setCurrentPage(PAGE_STATUS.SELECTION);
        setReservationResult(null);
    };

    switch (currentPage) {
        case PAGE_STATUS.FORM:
            return <ReservationForm selectedSeats={selectedSeats} onConfirm={handleConfirmReservation} onBack={() => setCurrentPage(PAGE_STATUS.SELECTION)} />;
        case PAGE_STATUS.RESULT:
            return <ResultPage isSuccess={reservationResult} onReset={resetProcess} />;
        case PAGE_STATUS.SELECTION:
        default:
            return <SeatSelection seats={seats} onSeatClick={handleSeatClick} onNext={() => setCurrentPage(PAGE_STATUS.FORM)} />;
    }
}


// --- 메인 앱 컴포넌트 ---
export default function App() {
  const [showBooking, setShowBooking] = useState(false);

  return (
    <Wrapper>
      <Card>
        {showBooking ? (
          <BookingProcess />
        ) : (
          <MainPage onStartBooking={() => setShowBooking(true)} />
        )}
      </Card>
    </Wrapper>
  );
}
