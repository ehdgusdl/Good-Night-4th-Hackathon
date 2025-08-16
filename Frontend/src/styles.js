import styled, { css } from 'styled-components';

// 공통 색상 팔레트
export const colors = {
  primary: '#6366F1', // indigo-500
  primaryHover: '#4F46E5',
  bgDarkest: '#0F172A', // gray-900
  bgDark: '#1F2937', // gray-800
  bgMid: '#374151', // gray-700
  gray400: '#9CA3AF',
  green500: '#22C55E',
  red500: '#EF4444',
  gray600: '#4B5563',
  gray700: '#374151',
};

// 전체 화면 중앙 정렬 래퍼
export const Wrapper = styled.div`
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: ${colors.bgDark};
  font-family: 'Inter', sans-serif;
  color: #fff;
  padding: 2rem;
`;

// 카드 컨테이너
export const Card = styled.div`
  width: 100%;
  max-width: 28rem; /* 448px */
  background: ${colors.bgDarkest};
  border-radius: 1rem;
  box-shadow: 0 20px 40px rgba(99, 102, 241, 0.2);
  padding: 24px; /* 기본 padding 1.5rem */

  @media (min-width: 640px) {
    padding: 32px;
  }
  @media (min-width: 768px) {
    padding: 40px;
  }
`;

// 기본 버튼
export const PrimaryButton = styled.button`
  width: 100%;
  background: ${colors.primary};
  color: #fff;
  padding: 0.75rem 1rem;
  font-size: 1rem;
  font-weight: 700;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: background 0.2s ease, transform 0.2s ease;

  &:hover:not(:disabled) {
    background: ${colors.primaryHover};
    transform: scale(1.03);
  }
  &:disabled {
    background: ${colors.gray400};
    cursor: not-allowed;
  }
`;

// 헤딩, 텍스트
export const Heading = styled.h1`
  font-size: clamp(2rem, 5vw, 3rem);
  font-weight: 800;
  color: ${colors.primary};
  text-align: center;
  margin-bottom: 1.5rem;
`;

export const SubText = styled.p`
  font-size: 1rem;
  color: ${colors.gray400};
  text-align: center;
  margin-bottom: 0.5rem;
`;

// 그리드 및 기타 레이아웃
export const CenterColumn = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
  text-align: center;
`;

export const SeatGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 0.5rem;
  justify-items: center;
  width: 100%;
  margin-bottom: 1rem;
`;


// 좌석 상태 레전드
export const LegendContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  gap: 0.5rem 1.5rem;
  border-top: 1px solid ${colors.bgMid};
  padding-top: 1.5rem;
  margin-top: 2rem;
  font-size: 0.875rem;
`;

export const LegendItem = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
`;

export const ColorBox = styled.div`
  width: 1rem;
  height: 1rem;
  border-radius: 0.25rem;
  background: ${({ color }) => color};
`;

// 선택 좌석 요약 카드
export const SummaryCard = styled.div`
  background: ${colors.bgDarkest};
  padding: 1.5rem;
  margin-top: 2rem;
  border-radius: 0.75rem;
`;

export const SeatListBox = styled.div`
  background: ${colors.bgMid};
  padding: 0.75rem;
  border-radius: 0.5rem;
  min-height: 60px;
  display: flex;
  align-items: center;
`;

export const StageBar = styled.div`
  width: 100%;
  background: ${colors.bgMid};
  text-align: center;
  padding: 0.5rem 0;
  border-top-left-radius: 0.75rem;
  border-top-right-radius: 0.75rem;
  margin-bottom: 2rem;
`;

// 좌석 블록
// ---- 예약 폼 ----
export const FormTitle = styled.h1`
  font-size: clamp(1.5rem, 4vw, 2rem);
  font-weight: 700;
  color: ${colors.primary};
  text-align: center;
  margin-bottom: 1.5rem;
`;

export const FormContainer = styled.div`
  padding: 1.5rem;
  background: ${colors.bgMid};
  border-radius: 0.75rem;
`;

export const SectionTitle = styled.h3`
  font-size: 1rem;
  font-weight: 600;
  color: #d1d5db;
  margin-bottom: 0.5rem;
`;

export const SelectedSeatText = styled.p`
  color: ${colors.primary};
  font-weight: 700;
  margin-bottom: 1.5rem;
`;

export const InputBox = styled.div`
  margin-bottom: 1rem;
`;

export const Label = styled.label`
  display: block;
  margin-bottom: 0.5rem;
  color: #d1d5db;
`;

export const Input = styled.input`
  width: 100%;
  padding: 0.5rem 0.75rem;
  border-radius: 0.5rem;
  background: ${colors.gray700};
  border: 1px solid ${colors.gray600};
  color: #fff;
  font-size: 1rem;
  &:focus {
    outline: none;
    box-shadow: 0 0 0 2px ${colors.primary};
  }
`;

export const ButtonRow = styled.div`
  display: flex;
  gap: 1rem;
`;

export const SecondaryButton = styled(PrimaryButton)`
  background: ${colors.gray600};
  &:hover:not(:disabled) {
    background: ${colors.gray700};
  }
`;

export const SeatBlock = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 3.5rem;
  height: 3.5rem;
  border-radius: 0.5rem;
  font-size: 0.875rem;
  font-weight: 600;
  color: #fff;
  transition: transform 0.2s ease;
  cursor: pointer;

  ${({ status }) => {
    switch (status) {
      case 'unavailable':
        return css`
          background: ${colors.gray400};
          cursor: not-allowed;
        `;
      case 'selected':
        return css`
          background: ${colors.green500};
          &:hover {
            background: #16a34a; /* green-600 */
          }
        `;
      default:
        return css`
          background: ${colors.primary};
          &:hover {
            background: ${colors.primaryHover};
          }
        `;
    }
  }}

  &:hover {
    transform: scale(1.1);
  }
`;
