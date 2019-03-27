require 'spec_helper'

RSpec.describe 'Parking Lot Government Regulation' do
  let(:pty) { PTY.spawn('parking_lot') }

  before(:each) do
    run_command(pty, "create_parking_lot 5\n")
    run_command(pty, "park KA-01-HH-0001 Red\n")
    run_command(pty, "park KA-01-HH-0002 Green\n")
    run_command(pty, "park KA-01-HH-0003 Blue\n")
    run_command(pty, "park KA-01-HH-0004 Red\n")
    run_command(pty, "park KA-01-HH-0005 Red\n")
  end

  it "can get registration numbers for cars with colour" do
    run_command(pty, "registration_numbers_for_cars_with_colour Red\n")
    expect(fetch_stdout(pty)).to end_with("KA-01-HH-0001, KA-01-HH-0004, KA-01-HH-0005\n")
    run_command(pty, "registration_numbers_for_cars_with_colour White\n")
    expect(fetch_stdout(pty)).to end_with("Not found\n")
  end

  it "can get slot number for registration number" do
    run_command(pty, "slot_number_for_registration_number KA-01-HH-0001\n")
    expect(fetch_stdout(pty)).to end_with("1\n")
    run_command(pty, "slot_number_for_registration_number KA-01-HH-9999\n")
    expect(fetch_stdout(pty)).to end_with("Not found\n")
  end

  it "can get slot numbers for cars with colour" do
    run_command(pty, "slot_numbers_for_cars_with_colour Red\n")
    expect(fetch_stdout(pty)).to end_with("1, 4, 5\n")
    run_command(pty, "slot_numbers_for_cars_with_colour White\n")
    expect(fetch_stdout(pty)).to end_with("Not found\n")
  end
end
